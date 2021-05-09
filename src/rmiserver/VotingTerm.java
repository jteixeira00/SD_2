package rmiserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class VotingTerm extends Thread{

    private static String MULTICAST_ADDRESS;
    private int PORT;
    private static String SECONDARY_MULTICAST;
    private int PORT2;
    private boolean firstExec;
    private String tableNumber = "";
    String password = "";
    private UUID uuid;
    public void setUuid() {
        this.uuid = UUID.randomUUID();
    }
    static VotingTerm client;
    private String rmiport;
    private String rminame;
    private String registry;

    Timer timer;

    public static void main(String[] args){
        client = new VotingTerm(true);
        client.start();
    }
    public VotingTerm(boolean firstExec){
        this.firstExec = firstExec;

    }




    public void run() {
        MulticastSocket socket = null;
        setUuid();
        try {
            String s;
            Scanner in = new Scanner(System.in);
            String numeroUc;
            byte[] buffer;
            DatagramPacket packet;
            if (firstExec) {
                System.out.println("A que mesa de voto deseja ligar-se?");
                s = in.nextLine();
                //load properties
                GetPropertyValues properties = new GetPropertyValues();
                try {
                    properties.setPropValues();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MULTICAST_ADDRESS = properties.getMain_multicast_pool()+s;
                SECONDARY_MULTICAST = properties.getSecondary_multicast_pool()+s;
                PORT = properties.getPort1();
                PORT2 = properties.getPort2();
                tableNumber = s;

                this.firstExec = false;
                System.out.println("Terminal de voto conectado à mesa nº " + tableNumber);
            }
            String messagestr;
            MessageProtocol message;
            int eleicao;

            while (true) {
                socket = new MulticastSocket(PORT2);
                InetAddress group = InetAddress.getByName(SECONDARY_MULTICAST);
                socket.joinGroup(group);
                messagestr = "type|newterminal";
                buffer = messagestr.getBytes();
                packet = new DatagramPacket(buffer, buffer.length, group, PORT2);
                socket.send(packet);
                //comunica um novo terminal (callback)
                socket = new MulticastSocket(PORT);  // create socket and bind it
                group = InetAddress.getByName(MULTICAST_ADDRESS);
                socket.joinGroup(group);
                System.out.println("Terminal de voto aguardando pedido de conexão");
                //aguarda uma mensagem a pedir um terminal livre
                do {

                    buffer = new byte[256];
                    packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    messagestr = new String(packet.getData(), 0, packet.getLength());
                    message = new MessageProtocol(messagestr);
                    System.out.println(messagestr);
                } while (!message.getType().equals("request"));
                numeroUc = message.getUsername();
                //responde a informar que está disponivel
                messagestr = "type|available;uuid|" + uuid.toString();
                buffer = messagestr.getBytes();
                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                socket.send(packet);

                //aguarda mensagem a desbloquear
                do {
                    buffer = new byte[256];
                    packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    messagestr = new String(packet.getData(), 0, packet.getLength());
                    message = new MessageProtocol(messagestr);
                } while (!message.getType().equals("unlock"));
                eleicao = message.getEleicao();


                if (message.getUuid().equals(uuid.toString())) {
                    System.out.println("Terminal desbloqueado");
                    System.out.println("UC Number:" + numeroUc);
                    System.out.println("Password");
                    TimerTask task= new TimerTask() {
                        public void run() {
                            client.stop();
                            System.out.println("Terminal bloqueou por inatividade, prima ENTER antes de inserir input");
                            client = new VotingTerm(false);
                            client.start();
                        }
                    };
                    timer = new Timer();
                    timer.schedule(task, 60 * 1000);
                    password = in.nextLine();
                    timer.cancel();
                    if(password.equals("") || password.equals("\n")){
                        System.out.println("[Password Inválida]");
                        run();
                        return;
                    }
                    
                    //conecta-se à segunda rede multicast
                    socket = new MulticastSocket(PORT2);
                    group = InetAddress.getByName(SECONDARY_MULTICAST);
                    socket.joinGroup(group);

                    //envia login info
                    messagestr = "uuid|" + uuid.toString() + ";type|login;number|" + numeroUc + ";password|" + password;
                    buffer = messagestr.getBytes();
                    packet = new DatagramPacket(buffer, buffer.length, group, PORT2);
                    socket.send(packet);

                    //recebe o status (sucesso no login ou nao)
                    do {
                        buffer = new byte[512];
                        packet = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packet);
                        messagestr = new String(packet.getData(), 0, packet.getLength());
                        message = new MessageProtocol(messagestr);

                    } while ((!message.getUuid().equals(uuid.toString())) || (!message.getType().equals("status")) || (message.getType().equals("login")));

                    if (message.getLogged().equals("on")) {
                        System.out.println("Escolha a lista em que pretende votar:");
                        //pedir listas candidatas
                        messagestr = "uuid|" + uuid.toString() + ";type|listas;eleicao|" + eleicao;
                        buffer = messagestr.getBytes();
                        packet = new DatagramPacket(buffer, buffer.length, group, PORT2);
                        socket.send(packet);

                        //recebe as listas candidatas
                        do {
                            buffer = new byte[1024];
                            packet = new DatagramPacket(buffer, buffer.length);
                            socket.receive(packet);
                            messagestr = new String(packet.getData(), 0, packet.getLength());
                            message = new MessageProtocol(messagestr);
                        } while (!message.getType().equals("item_list") || !message.getUuid().equals(this.uuid.toString()));


                        //imprime as listas candidatas

                        System.out.println("0 - Voto Nulo");
                        System.out.println("1 - Voto Branco");
                        for (Map.Entry<Integer, String> candidato : message.getCandidatos().entrySet()) {
                            Integer key = candidato.getKey() + 2;
                            String nome = candidato.getValue();
                            System.out.println(key + " - " + nome);
                        }

                        int choice = 0;
                        while (true) {
                            String choiceS = in.nextLine();
                            if (isParsable(choiceS)) {
                                choice = Integer.parseInt(choiceS);
                                break;
                            } else {
                                System.out.println("[Input Inválido: Tente de Novo]\n");
                            }
                        }

                        Date date = new Date();
                        String pattern = "MM/dd/yyyy HH:mm";
                        DateFormat df = new SimpleDateFormat(pattern);
                        String dataString = df.format(date);

                        messagestr = "uuid|" + uuid.toString() + ";type|voto;choice|" + choice + ";time|" + dataString + ";eleicao|" + eleicao + ";number|" + numeroUc;

                        buffer = messagestr.getBytes();
                        packet = new DatagramPacket(buffer, buffer.length, group, PORT2);
                        socket.send(packet);
                        //envia voto

                        do {
                            buffer = new byte[1024];
                            packet = new DatagramPacket(buffer, buffer.length);
                            socket.receive(packet);
                            messagestr = new String(packet.getData(), 0, packet.getLength());
                            message = new MessageProtocol(messagestr);
                        } while (!message.getType().equals("success"));


                        for (int i = 0; i < 50; i++) {
                            System.out.println("\n\n");
                        }
                        System.out.println("Success! Logging you off.");
                        run();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public static boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

}


