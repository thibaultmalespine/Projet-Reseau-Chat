import javax.swing.*;
import java.awt.*;

/**
 * Classe de l'interface graphique
 */
public class ChatGUI {

    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private JButton sendButton;

    public ChatGUI(Client client) {

        // Configurer l'interface graphique
        frame = new JFrame("Interface Client");
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
                client.out.println(message);
                textArea.append("Vous: " + message + "\n");
                textField.setText("");  // Réinitialiser le champ de saisie
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
}
