package translation;

import javax.swing.*;
import java.awt.event.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JSONTranslator jsonTranslator = new JSONTranslator();
            LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();

            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Country:"));
            JComboBox<String> countryComboBox = new JComboBox<>();
            for (String countryCode : jsonTranslator.getCountryCodes()) {
                countryComboBox.addItem(countryCodeConverter.fromCountryCode(countryCode));
            }
            countryComboBox.setEditable(false);
            countryComboBox.setPreferredSize(new java.awt.Dimension(200,30));
            countryPanel.add(countryComboBox);

            JPanel languagePanel = new JPanel();
            String[] items = new String[jsonTranslator.getCountryCodes().size()];
            int i = 0;
            for(String languageCode : jsonTranslator.getLanguageCodes()) {
                items[i++] = languageCodeConverter.fromLanguageCode(languageCode);
            }
            JList<String> languageList = new JList<>(items);
            languageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageList);


            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);



            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = languageList.getSelectedValue().toString();
                    String country = countryComboBox.getSelectedItem().toString();

                    String countryCode = countryCodeConverter.fromCountry(country);
                    String languageCode = languageCodeConverter.fromLanguage(language);
                    String result = jsonTranslator.translate(countryCode, languageCode);

                    //Debugging:
                    System.out.println(countryCode + " " + languageCode + " " + result);

                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
