import java.io.FileInputStream;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Driver extends Application {
	int coinsSize;// set number of coins inside it
	int coins[];// array that stores all coins
	int counter = 0;// counter to count number of coins entered in every enter pressed
	static boolean choice[][];// a boolean array helps to find coins picking order
	String s1 = "";// a string stores every coin entered when enter pressed
	String s2 = "";// a string stores the coins by player #1 and its indices
	Circle c[];// array of circles to simulate coins
	int taken1[];// it helps to know which coin is picked until now

	Text t[];// array of text to set the value of every coin inside the circles
	TranslateTransition tt[];// array used in simulation,moving every circle alone
	TranslateTransition ttt[];// array used in simulation,moving every value inside circle alone
	ScrollPane scrollPane = new ScrollPane();
	boolean flag = false;

	public static void main(String[] args) {

		launch(args);

	}

	static int[][] solve(int[] coins) {// solve the problem

		int n = coins.length;// number of coins
		int table[][] = new int[n][n];// table to store each cost

		// at first we need to fill the table zeros in the diagonal
		// at first the diagonal is from i to i it's only one coin
		// here the subset is one coin that mean
		for (int i = 0; i < n; i++) {
			table[i][i] = 0;
			choice[i][i] = true;

		}

		boolean turn = true;// who's turn? true if P#1 false if P#2
		int s = 1;

		for (s = 1; s < n; s++) {

			for (int i = 0; i + s < n; i++) {
				int scoreOne = table[i + 1][i + s];
				int scoreTwo = table[i][i + s - 1];

				if (turn) {
					table[i][i + s] = Math.max(coins[i] + scoreOne, coins[i + s] + scoreTwo);

					if (coins[i] + scoreOne > coins[i + s] + scoreTwo) {
						choice[i][i + s] = true;// if true means that the left one is picked
					}

					else
						choice[i][i + s] = false;// if false means that the right one is picked

				}

				// second player
				else {
					table[i][i + s] = Math.min(scoreOne, scoreTwo);
					if (scoreOne < scoreTwo) {
						choice[i][i + s] = true;// if true means that the left one is picked
					}

					else {
						choice[i][i + s] = false;// if false means that the right one is picked
					}

				}
			}

			// Give turn to the
			// other player.
			turn = !turn;

		}

		return table;
	}

//هان عندي اري فيها ترو و فولس  اذا كانت ترو معناها روح على شمال وزيد واحد اذا كان فولس معناها رايت وصغر السبست من اليمين بمقدار واحد
	static String getMoves(int n, boolean choice[][]) {
		String moves = "";
		int left = 0, right = n - 1;

		while (left <= right) {

			// if the bag is picked from left
			if (choice[left][right]) {// if true go to left
				moves = moves + 'L';
				left++;
			}

			else {// if true go to right
				moves = moves + 'R';
				right--;
			}
		}
		return moves;
	}

	@Override
	public void start(Stage arg0) throws Exception {
		Stage primaryStage = new Stage();
		Pane root = new Pane();// crate pane
		Scene scene = new Scene(root, 900, 750);// crate scene
		primaryStage.setTitle("the main menu");// set stage title
		root.setStyle("-fx-background-color: 	#808080;\r\n");// set the pane color using css

		Label lb1 = new Label("Enter Number of coins >");
		lb1.setFont(new Font("Arial", 25));
		lb1.setTextFill(Color.BLACK);
		lb1.setTranslateX(20);
		lb1.setTranslateY(20);

		Image myimage1 = new Image(new FileInputStream("solution.png"));// add the image
		ImageView myview1 = new ImageView(myimage1);// show
		// set the size
		myview1.setFitHeight(50);
		myview1.setFitWidth(50);
		Button Solve = new Button("Solve", myview1);// create a button and add the image
		// set size and position
		Solve.setTranslateX(20);
		Solve.setTranslateY(260);
		Solve.setMinWidth(200);
		Solve.setMinHeight(100);
		Solve.setMaxWidth(200);
		Solve.setMaxHeight(100);

		Label lb9 = new Label("P#1 coins");
		lb9.setFont(new Font("Arial", 20));
		lb9.setTextFill(Color.BLACK);
		lb9.setTranslateX(20);
		lb9.setTranslateY(630);
		lb9.setVisible(false);

		Label lb11 = new Label("P#2 coins");
		lb11.setFont(new Font("Arial", 20));
		lb11.setTextFill(Color.BLACK);
		lb11.setTranslateX(20);
		lb11.setTranslateY(700);
		lb11.setVisible(false);

		Image myimage2 = new Image(new FileInputStream("Simulation.png"));// add the image
		ImageView myview2 = new ImageView(myimage2);// show
		// set the size
		myview2.setFitHeight(50);
		myview2.setFitWidth(50);
		Button startSimulation = new Button("Start Simulation", myview2);// create a button and add the image
		// set size and position
		startSimulation.setTranslateX(20);
		startSimulation.setTranslateY(530);
		startSimulation.setMinWidth(250);
		startSimulation.setMinHeight(60);
		startSimulation.setMaxWidth(250);
		startSimulation.setMaxHeight(60);
		// set the button color and radius using css
		startSimulation.setStyle("-fx-background-color: #FFFFFF;\r\n" + "        -fx-background-radius:100;\r\n");
		startSimulation.setTextFill(Color.DARKBLUE);// set text color
		startSimulation.setFont(new Font("Arial", 20));// set font type
		startSimulation.setDisable(true);

		// set the button color and radius using css
		Solve.setStyle("-fx-background-color: #FFFFFF;\r\n" + "        -fx-background-radius:100;\r\n");
		Solve.setTextFill(Color.DARKBLUE);// set text color
		Solve.setFont(new Font("Arial", 20));// set font type
		Solve.setDisable(true);

		Label lb6 = new Label("only positive numbers allowed!");
		lb6.setFont(new Font("Arial", 22));
		lb6.setTextFill(Color.RED);
		lb6.setTranslateX(20);
		lb6.setTranslateY(380);
		lb6.setVisible(false);

		Label lb12 = new Label(
				"Warning!!! : if you enter more than 14  coin \n, the coins after coin 14 will be not visable");
		lb12.setFont(new Font("Arial", 18));
		lb12.setTextFill(Color.RED);
		lb12.setTranslateX(20);
		lb12.setTranslateY(370);
		lb12.setVisible(false);

		Label lb7 = new Label("only even positive numbers allowed!");
		lb7.setFont(new Font("Arial", 22));
		lb7.setTextFill(Color.RED);
		lb7.setTranslateX(20);
		lb7.setTranslateY(380);
		lb7.setVisible(false);

		TextField t2 = new TextField();
		t2.setDisable(true);

		TextArea info = new TextArea();
		info.setFont(new Font("Arial", 20));
		info.setTranslateX(400);
		info.setTranslateY(230);
		info.setMinSize(450, 270);
		info.setMaxSize(450, 270);
		info.setEditable(false);

		TextField t1 = new TextField();
		t1.setTranslateX(20);
		t1.setTranslateY(60);
		t1.setFont(new Font("Arial", 25));
		t1.setStyle("-fx-background-color: #eeffff;\r\n" + "        -fx-background-radius:100;\r\n");

		TextArea tt1 = new TextArea();
		tt1.setFont(new Font("Arial", 20));
		tt1.setTranslateX(10);
		tt1.setTranslateY(420);
		tt1.setMinSize(360, 70);
		tt1.setMaxSize(360, 70);
		tt1.setEditable(false);

		scrollPane.setTranslateX(120);
		scrollPane.setTranslateY(620);
		scrollPane.setPrefSize(700, 120);
		scrollPane.setVisible(false);

		TextArea tt2 = new TextArea();
		tt2.setFont(new Font("Arial", 20));
		tt2.setTranslateX(400);
		tt2.setTranslateY(110);
		tt2.setMinSize(450, 70);
		tt2.setMaxSize(450, 70);
		tt2.setEditable(false);

		Label lb8 = new Label("");
		lb8.setFont(new Font("Arial", 25));
		lb8.setTextFill(Color.BLUE);
		lb8.setTranslateX(630);
		lb8.setTranslateY(20);

		Group circles = new Group();

		t1.setText("");
		t1.setOnKeyTyped(e -> {// if a number of coin entered
			startSimulation.setDisable(true);// make sure simulation is disabled
			s1 = "";// make sure the coins entered before is deleted
			s2 = "";// make sure the coins picked by me before is deleted
			tt1.setText("");// clear the text area of the coins entered before
			tt2.setText("");// clear the text area of the coins picked by me before
			info.setText("");// clear the DP table
			lb8.setText("");// clear the maximum amount of money i can get
			lb9.setVisible(false);// make label of p#1 coins in simulation invisible
			lb11.setVisible(false);// make label of p#2 coins in simulation invisible
			circles.setVisible(false);// make the circles in the simulation invisible
			lb12.setVisible(false);// make the warning label invisible
			scrollPane.setVisible(false);
			lb7.setVisible(false);// make sure the error of that the even positive numbers is only acceptable
			Solve.setDisable(true);// make sure the solve button is invisible
			if (isNumeric(t1.getText()) == true) {// check if the number entered is an integer
				int number = Integer.parseInt(t1.getText());// convert string into integer
				if (number % 2 == 0 && number > 0) {// check if the number is positive even and not zero
					if (number >= 16) {// case number of coins is more than 14 show an warning that the scene size is
										// not enough
						lb12.setVisible(true);
					}

					t2.setDisable(false);//
					coinsSize = number;
					coins = new int[coinsSize];
					choice = new boolean[coinsSize][coinsSize];
					counter = 0;
				} else {
					t2.setDisable(true);
					if (t1.getText().compareTo("") != 0) {
						lb7.setVisible(true);
					}

				}
			} else {
				if (t1.getText().compareTo("") != 0) {
					lb7.setVisible(true);
				}

				t2.setDisable(true);
			}

		});

		Label lb2 = new Label("Enter the coins one by one >");
		lb2.setFont(new Font("Arial", 25));
		lb2.setTextFill(Color.BLACK);
		lb2.setTranslateX(20);
		lb2.setTranslateY(140);

		t2.setTranslateX(20);
		t2.setTranslateY(180);
		t2.setFont(new Font("Arial", 25));
		t2.setStyle("-fx-background-color: #eeffff;\r\n" + "        -fx-background-radius:100;\r\n");

		t2.setOnKeyPressed(e -> {
			lb6.setVisible(false);
			lb12.setVisible(false);

			if (e.getCode().equals(KeyCode.ENTER)) {

				if (isNumeric(t2.getText()) == true) {

					int number = Integer.parseInt(t2.getText());
					if (number > 0) {
						coins[counter] = number;
						counter++;

						if (counter != coinsSize) {
							s1 = s1 + number + " , ";
						} else {
							s1 = s1 + number;
						}
						tt1.setText(s1);

						t2.setText("");
						if (counter == coinsSize) {

							t2.setDisable(true);
							Solve.setDisable(false);
							counter = 0;
						}
					} else {
						if (t1.getText().compareTo("") != 0) {
							lb6.setVisible(true);
						}
					}

				} else {
					if (t1.getText().compareTo("") != 0) {
						lb6.setVisible(true);
					}

					Solve.setDisable(true);
				}
			}

		});

		Label lb3 = new Label("maximum amount > ");
		lb3.setFont(new Font("Arial", 25));
		lb3.setTextFill(Color.BLACK);
		lb3.setTranslateX(400);
		lb3.setTranslateY(20);

		Label lb4 = new Label("Player #1 Coins (<value,index>) > ");
		lb4.setFont(new Font("Arial", 25));
		lb4.setTextFill(Color.BLACK);
		lb4.setTranslateX(400);
		lb4.setTranslateY(70);

		Label lb5 = new Label("Dynamic Programming Table > ");
		lb5.setFont(new Font("Arial", 25));
		lb5.setTextFill(Color.BLACK);
		lb5.setTranslateX(400);
		lb5.setTranslateY(180);

		Line line = new Line();
		line.setStartX(380f);
		line.setStartY(20f);
		line.setEndX(380f);
		line.setEndY(520f);

		Line line2 = new Line();
		line2.setStartX(0f);
		line2.setStartY(520f);
		line2.setEndX(900f);
		line2.setEndY(520f);
		Solve.setOnAction(e -> {
			int table[][] = new int[coinsSize][coinsSize];
			startSimulation.setDisable(false);
			table = solve(coins);
			lb8.setText("" + table[0][coinsSize - 1]);
			String tableS = new String("");
			s2 = "";
			taken1 = new int[coinsSize];
			// int max=table[0][coinsSize-1];
			tableS = tableS + "\t";
			for (int i = 0; i < table.length; i++) {
				tableS = tableS + i + "\t";
			}
			tableS = tableS + "\n";
			for (int i = 0; i < table.length; i++) {
				tableS = tableS + "\t-";
			}
			for (int i = 0; i < coinsSize; i++) {
				tableS = tableS + "\n" + i + " |\t";

				for (int j = 0; j < coinsSize; j++) {

					tableS = tableS + table[i][j] + "\t";

				}

			}
			int arr[] = new int[coinsSize / 2];
			arr = myCoins(coins);

			for (int i = 0; i < arr.length; i++) {
				if (i < arr.length - 1) {
					s2 = s2 + "<" + coins[arr[i]] + "," + arr[i] + ">,";
				} else {
					s2 = s2 + "<" + coins[arr[i]] + "," + arr[i] + ">";
				}
			}
			info.setText(tableS);
			// lb9.setText(tableS);
			// lb10.setText(s2);
			tt2.setText(s2);

			circles.getChildren().clear();
			c = createCoins();
			t = new Text[coinsSize];
			for (int i = 0; i < coinsSize; i++) {
				String s = "" + coins[i];
				t[i] = new Text(c[i].getCenterX() - 5, c[i].getCenterY() + 2, s);
				t[i].setFont(new Font("Arial", 10));
				circles.getChildren().addAll(c[i], t[i]);
			}
			if (coinsSize < 16) {
				if (flag) {
					circles.setTranslateX(150);
					circles.setTranslateY(655);
				}
				circles.setVisible(true);
				root.getChildren().add(circles);
				// scrollPane.setVisible(true);
			}
			if (coinsSize >= 16) {
				circles.setVisible(true);
				scrollPane.setVisible(true);
				scrollPane.setContent(circles);
				flag = true;

			}

		});

		startSimulation.setOnAction(e -> {

			lb9.setVisible(true);
			lb11.setVisible(true);

			counterFlag = 0;
			for (int i = 0; i < taken1.length; i++) {
				taken1[i] = 0;
			}
			SequentialTransition seqT = new SequentialTransition();
			SequentialTransition seqT2 = new SequentialTransition();
			for (int i = 0; i < c.length; i++) {
				counterFlag = i;
				tt = new TranslateTransition[coinsSize];
				ttt = new TranslateTransition[coinsSize];

				int turn = whosTurn(i);

				if (turn > 0 && turn != 500) {
					tt[i] = new TranslateTransition();
					tt[i] = new TranslateTransition(Duration.seconds(1.5), c[turn]);

					tt[i].setFromX(0);
					tt[i].setFromY(0);
					tt[i].setByY(-35);
					tt[i].setCycleCount(1);
					seqT.getChildren().add(tt[i]);
					ttt[i] = new TranslateTransition();
					ttt[i] = new TranslateTransition(Duration.seconds(1.5), t[turn]);
					ttt[i].setFromX(0);
					ttt[i].setFromY(0);
					ttt[i].setByY(-35);
					ttt[i].setCycleCount(1);
					seqT2.getChildren().add(ttt[i]);

				}
				if (turn < 0 && turn != -500) {
					tt[i] = new TranslateTransition();
					tt[i] = new TranslateTransition(Duration.seconds(1.5), c[-1 * turn]);

					tt[i].setFromX(0);
					tt[i].setFromY(0);
					tt[i].setByY(35);
					tt[i].setCycleCount(1);
					seqT.getChildren().add(tt[i]);
					ttt[i] = new TranslateTransition();
					ttt[i] = new TranslateTransition(Duration.seconds(1.5), t[-1 * turn]);
					ttt[i].setFromX(0);
					ttt[i].setFromY(0);
					ttt[i].setByY(35);
					ttt[i].setCycleCount(1);
					seqT2.getChildren().add(ttt[i]);

				}
				if (turn == 500) {
					tt[i] = new TranslateTransition();
					tt[i] = new TranslateTransition(Duration.seconds(1.5), c[0]);

					tt[i].setFromX(0);
					tt[i].setFromY(0);
					tt[i].setByY(-35);
					tt[i].setCycleCount(1);
					seqT.getChildren().add(tt[i]);
					ttt[i] = new TranslateTransition();
					ttt[i] = new TranslateTransition(Duration.seconds(1.5), t[0]);
					ttt[i].setFromX(0);
					ttt[i].setFromY(0);
					ttt[i].setByY(-35);
					ttt[i].setCycleCount(1);
					seqT2.getChildren().add(ttt[i]);

				}
				if (turn == -500) {

					tt[i] = new TranslateTransition();
					tt[i] = new TranslateTransition(Duration.seconds(1.5), c[0]);

					tt[i].setFromX(0);
					tt[i].setFromY(0);
					tt[i].setByY(35);
					tt[i].setCycleCount(1);
					seqT.getChildren().add(tt[i]);
					ttt[i] = new TranslateTransition();
					ttt[i] = new TranslateTransition(Duration.seconds(1.5), t[0]);
					ttt[i].setFromX(0);
					ttt[i].setFromY(0);
					ttt[i].setByY(35);
					ttt[i].setCycleCount(1);
					seqT2.getChildren().add(ttt[i]);

				}

			}
			seqT.play();
			seqT2.play();

		});

		root.getChildren().addAll(lb1, t1, lb2, t2, Solve, lb3, lb4, lb5, line, line2, lb6, lb7, lb8, info, tt1, tt2,
				startSimulation, circles, scrollPane, lb9, lb11, lb12);// add the controls to the pane
		primaryStage.setScene(scene);// set the scene
		primaryStage.show();

	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			int i = Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static int[] myCoins(int coins[]) {// return an array of indexes of coins that i choose
		int coinsSize = coins.length;
		int arr[] = new int[coinsSize / 2];
		int taken[] = new int[coinsSize];
		String s = getMoves(coinsSize, choice);
		for (int i = 0; i < s.length(); i++) {

			if (i % 2 == 0) {

				if (s.charAt(i) == 'R') {
					for (int j = coins.length - 1; j >= 0; j--) {

						if (taken[j] == 0) {
							arr[i / 2] = j;
							taken[j] = 1;
							break;
						}
					}
				} else {
					for (int j = 0; j <= coins.length - 1; j++) {
						if (taken[j] == 0) {
							arr[i / 2] = j;
							taken[j] = 1;
							break;
						}
					}
				}
			} else {
				if (s.charAt(i) == 'R') {
					for (int j = coins.length - 1; j >= 0; j--) {

						if (taken[j] == 0) {
							taken[j] = 1;
							break;
						}
					}
				} else {
					for (int j = 0; j <= coins.length - 1; j++) {
						if (taken[j] == 0) {
							taken[j] = 1;
							break;
						}
					}
				}
			}
		}

		return arr;
	}

	public Circle[] createCoins() {
		Circle circles[] = new Circle[coinsSize];
		for (int i = 0; i < circles.length; i++) {
			circles[i] = new Circle();
			circles[i].setRadius(20);
			circles[i].setFill(Color.WHITE);
			circles[i].setCenterY(680);
			circles[i].setCenterX(150 + 50 * i);
			circles[i].setStroke(Color.BLACK);
			circles[i].setStrokeWidth(3);

		}

		return circles;
	}

	int counterFlag = 0;

	public int whosTurn(int time) {// this return the index of the coin that need to move it now ,if it's for
									// player one return a positive number, else if it is for the player tow return
									// a negative number if at index zero return 500 or -500

		String s = getMoves(coinsSize, choice);
		int coinsSize = coins.length;
		int taken[] = taken1;
		for (int i = time; i < s.length(); i++) {

			if (i % 2 == 0) {

				if (s.charAt(i) == 'R') {
					for (int j = coins.length - 1; j >= 0; j--) {

						if (taken[j] == 0) {

							taken[j] = 1;
							taken1[j] = 1;
							counterFlag++;
							if (j == 0) {
								return 500;
							}
							return j;

						}
					}
				} else {
					for (int j = 0; j <= coins.length - 1; j++) {
						if (taken[j] == 0) {

							taken[j] = 1;
							taken1[j] = 1;
							counterFlag++;
							if (j == 0) {
								return 500;
							}
							return j;
						}
					}
				}
			} else {
				if (s.charAt(i) == 'R') {
					for (int j = coins.length - 1; j >= 0; j--) {

						if (taken[j] == 0) {
							taken[j] = 1;
							taken1[j] = 1;
							counterFlag++;
							if (j == 0) {
								return -500;
							}
							return -1 * j;
						}
					}
				} else {
					for (int j = 0; j <= coins.length - 1; j++) {
						if (taken[j] == 0) {
							taken[j] = 1;
							taken1[j] = 1;
							counterFlag++;
							if (j == 0) {
								return -500;
							}
							return -1 * j;
						}
					}
				}
			}
		}
		return 1;

	}

}
