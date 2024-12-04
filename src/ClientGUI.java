import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Classe de l'interface graphique
 */
public class ClientGUI {

    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private JButton sendButton;

    public ClientGUI(Client client) {

         // Demander le client.pseudo à l'utilisateur
        client.pseudo = JOptionPane.showInputDialog(null, "Entrez votre pseudo :", "Pseudo", JOptionPane.QUESTION_MESSAGE).replaceAll(" ", "_");

        // Vérifier que le client.pseudo n'est pas vide ou annulé
        if (client.pseudo == null || client.pseudo.trim().isEmpty()) {
            client.pseudo = "Anonyme";
        }

        // Configurer l'interface graphique
        frame = new JFrame("Interface Client - " + client.pseudo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        textField = new JTextField(25);
        sendButton = new JButton("Envoyer");

        // Ajouter un gestionnaire d'événements pour le bouton "Envoyer"
        sendButton.addActionListener(e -> {
            String message = textField.getText();
            if (!message.trim().isEmpty()) {
                client.out.println(AES.crypteMessage(message, client.aesKey));
                getMessages(message,"Vous");
                textField.setText("");  // Réinitialiser le champ de saisie
            }
            if (message.equals("bye")) {
                frame.dispose();
                System.exit(0);
            }
        });

        // Ajouter un ActionListener sur le champ de texte pour la touche "Entrée"
        textField.addActionListener(e -> sendButton.doClick());  // Simuler un clic sur le bouton "Envoyer" en appuyant sur "Entrée"

        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(sendButton);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);

}

    public void getMessages(String message, String id) {
        textArea.append( id+": " + message + "\n");
    }



}
