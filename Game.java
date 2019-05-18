import java.util.Scanner;
import java.util.Arrays;
public class Game{
    private int _min;
    private int _max;
    private int _amount;
    private int[] _chosen;
    private int[][] _different_possible_sums;
    private boolean _checked_for_solution;
    private boolean _found_solution;
    private int[][] _solution;
    private Scanner scanner = new Scanner(System.in);
    public Game(){
        _min = 10;
        _max = 36;
        _amount = 8;
        _chosen = new int[_amount];
        _different_possible_sums = new int[(2*_max-_amount+2)*(_amount-1)/2][_amount];
        _checked_for_solution = false;
        _found_solution = false;
        _solution = new int[2][_amount];
    }
    public Game(int min, int max, int amount){
        if(0 <= min && min < max && amount >= 2 && amount <= max-min+1){
            _min = min;
            _max = max;
            _amount = amount;
        }
        else{
            _min = 10;
            _max = 36;
            _amount = 8;
        }
        _chosen = new int[_amount];
        _different_possible_sums = new int[(2*_max-_amount+2)*(_amount-1)/2][_amount];
        _checked_for_solution = false;
        _found_solution = false;
        _solution = new int[2][_amount];
    }
    public int getMin(){
        return _min;
    }
    public int getMax(){
        return _max;
    }
    public int getInputAmount(){
        return _amount;
    }
    public int[] askForNumbers(){
        int i = 0;
        while(i<_amount){
            int input;
            try{
                input = Integer.valueOf(scanner.nextLine());
            }
            catch(Exception e){
                System.out.println("Not an int, please enter an int");
                scanner.reset();
                continue;
            }
            boolean broke = false;
            if(input < _min){
                System.out.println("The number you chose is too small! Please choose a bigger number.");
                continue;
            }
            if(input > _max){
                System.out.println("The number you chose is too big! Please choose a smaller number.");
                continue;
            }
            for(int j = 0; j < _amount; j++){
                if(_chosen[j] == input){
                    System.out.println("You already chose that number! Please choose another one.");
                    broke = true;
                    break;
                }
            }
            if(broke == true){
                continue;
            }
            _chosen[i] = input;
            i++;
        }
        return _chosen; //aliasing isn't a problem here because we want to let main be able to change some of the chosen numbers
    }
    public boolean findSolution(){
        int[] comb = new int[_amount];
        int[] empty = new int[_amount];
        while(true){
            if(!increaseComb(comb)){
                _checked_for_solution = true;
                _found_solution = false;
                return false;
            }
            int sum = 0;
            for(int i = 0; i < _amount; i++){
                if(comb[i] == 1){
                    sum+=_chosen[i];
                }
            }
            if(Arrays.equals(_different_possible_sums[sum-_min],empty)){
                for(int i = 0; i < _amount; i++){
                    _different_possible_sums[sum-_min][i] = comb[i];
                }
            }
            else{
                int index = 0;
                for(int i = 0; i < _amount; i++){
                    if(comb[i] == 1){
                        _solution[0][index] = _chosen[i];
                        index++;
                    }
                }
                index = 0;
                for(int i = 0; i < _amount; i++){
                    if(_different_possible_sums[sum-_min][i] == 1){
                        _solution[1][index] = _chosen[i];
                        index++;
                    }
                }
                for(int i = 0; i < _amount; i++){
                    if(_solution[0][i] == 1 && _solution[1][i] == 1){
                        _solution[0][i] = 0;
                        _solution[1][i] = 0;
                    }
                }
                break;
            }
        }
        _checked_for_solution = true;
        _found_solution = true;
        return true;
    }
    public void printSolution(){
        if(_checked_for_solution == false){
            System.out.println("You didn't check for a solution, please try again after checking for one.");
        }
        if(_checked_for_solution == true && _found_solution == false){
            System.out.println("There is no solution.");
        }
        if(_checked_for_solution == true && _found_solution == true){
            String print = "";
            print+=_solution[0][0];
            for(int i = 1; i < _amount; i++){
                if(_solution[0][i] != 0){
                    print+=" + " + _solution[0][i];
                }
            }
            print+=" = ";
            print+=_solution[1][0];
            for(int i = 1; i < _amount; i++){
                if(_solution[1][i] != 0){
                    print+=" + " + _solution[1][i];
                }
            }
            System.out.println(print);
        }
    }
    public void play(){
        System.out.println("Hello! This project shows what is the Pigeonhole principle.\n"+
                            "Please enter " + getInputAmount() + " whole numbers between " + getMin() + " and " + getMax() + " and press enter after each number.");
        askForNumbers();
        findSolution();
        printSolution();
    }
    private boolean increaseComb(int[] comb){
        int i = _amount-1;
        while(comb[i] == 1){
            comb[i] = 0;
            i--;
            if(i<0){
                return false;
            }
        }
        comb[i] = 1;
        return true;
    }
}
