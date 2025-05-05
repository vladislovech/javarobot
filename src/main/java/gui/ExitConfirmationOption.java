package gui;

public enum ExitConfirmationOption {
    YES("Да"),
    NO("Нет");

    private final String text;

    ExitConfirmationOption(String text) {
        this.text = text;
    }

    public static String[] getOptions(LocalizationManager localizationManager) {
        ExitConfirmationOption[] values = values();
        String[] options = new String[values.length];
        for(int i = 0; i < values.length; i++){
            options[i] = localizationManager.getString(LocalizationKeys.EXIT_OPTION + values[i].name().toLowerCase());
        }
        return options;
    }
}
