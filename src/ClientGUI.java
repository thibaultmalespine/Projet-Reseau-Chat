import java.awt.*;
import javax.swing.*;

/**
 * Classe de l'interface graphique
 */
public class ClientGUI {

    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private JButton sendButton;

    /**
     * Constructeur pour générer l'interface graphique
    */
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
        frame.setSize(500, 500);

        // Ajouter un panneau principal avec un layout BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 228, 196)); // Couleur de fond chaleureuse

        // Créer une zone de texte pour afficher les messages
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textArea.setBackground(new Color(255, 250, 240)); // Couleur de fond légèrement différente
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Créer un panneau pour les contrôles d'envoi de message
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(new Color(255, 228, 196));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        textField = new JTextField();
        textField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sendButton = new JButton("Envoyer");
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        sendButton.setBackground(new Color(255, 165, 0)); // Couleur chaude pour le bouton
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);

        // Ajouter un gestionnaire d'événements pour le bouton "Envoyer"
        sendButton.addActionListener(e -> {
            String message = textField.getText();
            if (!message.trim().isEmpty()) {
                client.out.println(AES.crypteMessage(message, client.aesKey));
                getMessages(message, "Vous");
                textField.setText("");  // Réinitialiser le champ de saisie
            }
            if (message.equals("bye")) {
                frame.dispose();
                System.exit(0);
            }
        });

        // Ajouter un ActionListener sur le champ de texte pour la touche "Entrée"
        textField.addActionListener(e -> sendButton.doClick());  // Simuler un clic sur le bouton "Envoyer" en appuyant sur "Entrée"

        // Disposition des composants dans le panneau d'entrée
        inputPanel.add(textField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Ajouter les composants au panneau principal
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }


    /**
     * Méthode pour ajouter le message reçue au textArea
    */
    public void getMessages(String message, String id) {
        textArea.append( id + ": " + message + "\n");
    }



}
