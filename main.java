import java.util.Scanner;
public class main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Game game;
        while(true){
            game = new Game();
            game.play();
            game = null;
            String ans = "";
            while(!ans.equals("YES") && !ans.equals("NO")){
                System.out.println("Do you want to play again?\n"+
                                    "YES / NO");
                ans = scanner.nextLine().toUpperCase();
            }
            if(ans.equals("NO")){
                break;
            }
        }
    }
}