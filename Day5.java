import java.util.*;

public class Day5{
    public static void main(String[] args){
        Book book = new Book();
        Borrower stu1 = new Borrower(book, "A");
        Borrower stu2 = new Borrower(book, "B");
        Borrower stu3 = new Borrower(book, "C");

        Thread th1 = new Thread(stu1);
        Thread th2 = new Thread(stu2);
        Thread th3 = new Thread(stu3);

        th1.start();
        th2.start();
        th3.start();
    }
}

class Book{
    private String borrower = "EMPTY";
    private boolean lending = false;

    public synchronized boolean addBook(String name){
        Util utl = new Util();
        utl.rand_pause(500);
        if(lending == false){
            this.borrower = name;
            this.lending = true;
            System.out.println(name + "さん、お貸しします");
            return true;
        }else{
            System.out.println(name + "さん、借りられません。" + this.borrower + "さんが貸出中です");
            return false;
        }
    }

    public synchronized void retBook(String name){
        Util utl = new Util();
        utl.rand_pause(500);
        if(lending == true && borrower == name){
            this.borrower = "EMPTY";
            this.lending = false;
            System.out.println(name + "さん、返却しました");
        }else{
            System.out.println(name + "さん、おかしい！");
        }
    }
}

class Borrower implements Runnable{
    Book book;
    String name;

    Borrower(Book book){
        this.book = book;
        this.name = "ゲスト";
    }

    Borrower(Book book, String name){
        this.book = book;
        this.name = name;
    }

    public void run(){
        for(int i=0; i<10; i++){
            if(book.addBook(this.name)){
                book.retBook(this.name);
            }
        }
    }
}

class Util	{
	void rand_pause(int t) {
		try {
			int n = (int)(Math.random()*t);
			Thread.sleep(n);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}