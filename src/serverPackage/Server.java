package serverPackage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Serveur en attente de connexion...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connecté.");

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                int op1 = in.readInt();
                char operateur = in.readChar();
                int op2 = in.readInt();
                if (op1 == 0 && op2 == 0 && operateur == '0') {
                    System.out.println("Fin de communication demandée par le client.");
                    break;
                }

                double resultat = 0;
                boolean valide = true;

                switch (operateur) {
                    case '+': resultat = op1 + op2; break;
                    case '-': resultat = op1 - op2; break;
                    case '*': resultat = op1 * op2; break;
                    case '/':
                        if (op2 != 0) resultat = (double) op1 / op2;
                        else {
                            out.writeUTF("Erreur : division par zéro !");
                            valide = false;
                        }
                        break;
                    default:
                        out.writeUTF("Erreur : opérateur invalide !");
                        valide = false;
                }

                if (valide) out.writeUTF("Résultat = " + resultat);
                System.out.println("Opération reçue : " + op1 + " " + operateur + " " + op2);
            }

            in.close();
            out.close();
            clientSocket.close();
            System.out.println("Serveur arrêté.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
