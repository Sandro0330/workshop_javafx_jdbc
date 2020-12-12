package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
		
	}
	
	//Retornado um null caso o número não seja retornado corretamente
	public static Integer tryParseToInt(String str) {
		try {
			
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
