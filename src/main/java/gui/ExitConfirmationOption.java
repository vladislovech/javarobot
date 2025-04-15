package gui;

public enum ExitConfirmationOption {
    YES("Да"),
    NO("Нет");

    private final String text;

    ExitConfirmationOption(String text) {
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public static String[] getOptions(){
        ExitConfirmationOption[] values = values();
        String[] options = new String[values.length];
        for(int i = 0; i < values.length; i++){
            options[i] = values[i].getText();
        }

        return options;
    }
}
