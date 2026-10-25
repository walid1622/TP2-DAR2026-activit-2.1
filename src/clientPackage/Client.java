package clientPackage;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Client connecté au serveur.");

            while (true) {
                System.out.print("Entrez une opération ou 0 0 0 pour quitter : ");
                int op1 = sc.nextInt();
                char operateur = sc.next().charAt(0);
                int op2 = sc.nextInt();
                out.writeInt(op1);
                out.writeChar(operateur);
                out.writeInt(op2);
                if (op1 == 0 && op2 == 0 && operateur == '0') {
                    System.out.println("Fin de la communication.");
                    break;
                }

                String resultat = in.readUTF();
                System.out.println("Réponse du serveur : " + resultat);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

