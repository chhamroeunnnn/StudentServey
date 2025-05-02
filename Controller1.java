import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;

public class Controller1 implements Initializable {
    @FXML
    private Button Button_Submit;

    @FXML
    private CheckBox Checkbox_c;

    @FXML
    private CheckBox Checkbox_f;

    @FXML
    private CheckBox Checkbox_pr;

    @FXML
    private DatePicker Date_1;

    @FXML
    private Label Label_status;

    @FXML
    private RadioButton Radio_AMS;

    @FXML
    private RadioButton Radio_GGG;

    @FXML
    private RadioButton Radio_GTR;

    @FXML
    private TextField Tectfield_1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button_Submit.setOnAction(e -> {
            String name = Tectfield_1.getText();
            String date = (Date_1.getValue() != null) ? Date_1.getValue().toString() : "";

            // Determine selected department
            String department = "No department selected";
            if (Radio_GTR.isSelected()) {
                department = "ITC GTR";
            } else if (Radio_GGG.isSelected()) {
                department = "ITC GGG";
            } else if (Radio_AMS.isSelected()) {
                department = "ITC AMS";
            }

            // Collect selected courses
            StringBuilder coursesBuilder = new StringBuilder();
            if (Checkbox_pr.isSelected()) {
                coursesBuilder.append("Programming ");
            }
            if (Checkbox_c.isSelected()) {
                coursesBuilder.append("Controller ");
            }
            if (Checkbox_f.isSelected()) {
                coursesBuilder.append("Filter ");
            }
            String courses = coursesBuilder.length() > 0 ? coursesBuilder.toString().trim() : "No course selected";

            // Validate inputs
            if (!name.isEmpty() && !date.isEmpty() &&
                (Radio_GTR.isSelected() || Radio_GGG.isSelected() || Radio_AMS.isSelected()) &&
                (Checkbox_pr.isSelected() || Checkbox_c.isSelected() || Checkbox_f.isSelected())) {

                // Write to CSV
                writeToCSV(name, date, department, courses);

                // Update status label
                Label_status.setText("All fields are valid");
            } else {
                Label_status.setText("Please fill all fields.");
            }
        });
    }

    public void writeToCSV(String name, String date, String department, String courses) {
        try (FileWriter writer = new FileWriter("SurveyResult.csv", true)) {
            // If file is empty, write header first
            java.io.File file = new java.io.File("SurveyResult.csv");
            if (file.length() == 0) {
                writer.write("Name,Date,Department,Courses\n");
            }

            // Write data
            writer.write(name + ",");
            writer.write(date + ",");
            writer.write(department + ",");
            writer.write("\"" + courses + "\"\n"); // Enclose courses in quotes to handle commas
            writer.flush();

            System.out.println("Data written to SurveyResult.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
