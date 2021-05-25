import forms.ServiceForm;

public class Application {
    public static void main(String[] args)
    {
        ServiceForm serviceForm = new ServiceForm();
        serviceForm.setVisible(true);
        serviceForm.pack();
    }
}
