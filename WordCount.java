/**
* WordCount.java
*
* @version   $Id: WordCount.java,v_2.0 2014/09/09 21:31:00
*
* @author    hhk9433 (Hrishikesh Karale)
*
* Revisions:
*      Initial revision
*/
import java.util.Scanner;
import java.io.File;

/*
 * This program finds the count of each printable character in the file "macbeth.txt"
 * Also no of words in the file and no of lines in the file are displayed
 */
public class WordCount
{
	//stores no of characters
	private int no_of_char=0;	
	//stores no of lines
	private int no_of_lines=0;
	//stores the no of occurrence of each printable character
	private int count_of_char[] = new int[95];	
	//stores possible words.
	private String s="";
	//used to store ascii value of printable character.
	private int compare=0;
	//used to count no of words.
	private int counter=0;
	
	
	/**
	 * This method checks if the string already exists in the array, if not then
	 * it adds it to the end of the array.
	 * @param s
	 * @param words
	 * @param count
	 */
	public void check(String s, String words[], int count[])
	{
		boolean placed=false;
		//iterates through the words and checks if the string already exists in the array
		for(int k=0; k<counter; k++)
			if(s.equals(words[k]))
			{
				placed= true;
				words[k]=s;
				count[k]++;
				s="";
				break;
			}
		//if it doesnt exist then add it to the end of the array.
		if(!placed)
		{
			words[counter]=s;
			count[counter++]++;
		}
	}
	
	/**
	 * Sorts the array or words according to word count and then saves it in a file.
	 * @param words
	 * @param count
	 */
	private void sortAndPrint(String words[], int count[])
	{		
		//stores count of word temporarily for swapping
		int replace=0;
		//used to compare with word count
		int max=100000000;
		//used to store word being swapped
		String swap= "";
		
		//sorts the words array
		for(int j=0; j<(counter); j++)
		{
			for(int i=j; i<(counter); i++)
			{
				max= count[j];
				swap= words[j];
				replace=count[i];
				if(replace>max)
				{
					words[j]= words[i];
					count[j]= replace;
					words[i]= swap;
					count[i]= max;
					max=replace;
					swap= words[i];
				}
			}
		}
		
		System.out.println("\n\nWords and their count:");
		
		//formats and prints the array of words in a descending order.
		for(int j=0; j<(counter); j++)
		{
			System.out.format("%-16s",words[j]);
			System.out.println(count[j]);
		}
	}
	
	
	public void scanfile(Scanner scr, String words[], int count[])
	{

		//checks if line is available to be read
		while ( scr.hasNext() )	
		{
			//stores the whole line as a string
			String line = scr.nextLine();
			
			//calculates no of characters in given line and adds to the previous count
			no_of_char = no_of_char + line.length();
			//increments no of lines for each line read
			no_of_lines++;	

			//calculates no of words and
			for(int i = 0 ;i < line.length() ; i++)	 
			{		
				compare= (int)line.charAt(i);
				
				//decrement total char for white spaces
				if(compare==32)
					no_of_char--;
				
				//counts no of occurrence of each printable character except white space.
				for (int j = 1 ; j < 95 ; j++)
				{
					if(compare<=(j+32))
					{
						//checks for special characters from ascii 33-64
						if (j<=32)
						{
							count_of_char[j]++;
							if(s!="")
							{
								check(s, words, count);
								s="";
							}
							break;
						}
						//checks for A-Z
						else if (j>32 && j<=58)
						{
							count_of_char[j]++;
							s=s+(char)compare;
							break;
						}
						//checks for special characters from ascii 91-96
						else if (j>58 && j<=64)
						{
							count_of_char[j]++;
							if(s!="")
							{
								check(s, words, count);
								s="";
							}
							break;
						}
						// checks for a-z
						else if(j>64 && j<=90)
						{
							count_of_char[j]++;
							s=s+(char)compare;
							break;
						}
						//checks for special characters from ascii 123-127
						else if( j>90 && j<=94)
						{
							count_of_char[j]++;
							if(s!="")
							{
								check(s, words, count);
								s="";
							}
							break;
						}
					}
				}
			}
			
		}

		//prints total no of lines
		System.out.println("no of lines : " + no_of_lines);	
		//prints total no of words
		System.out.println("no of words : " + (counter));	
		//prints total no of characters
		System.out.println("no of characters : "+ no_of_char+"\n");	
		System.out.println("Following are the characters that occurred in the file.txt and their count");

		//prints printable character and their count
		for (int i = 0 ; i < 95 ; i++)	
			if(count_of_char[i]!=0)
				System.out.println((char)(i+32) + " : " + count_of_char[i]);
		
		 sortAndPrint(words, count);
		//close scanner
		scr.close();
	}
	
    public static void main( String[] args ) 
    {

    	//stores words and their of occurance
    	String words[]= new String[10000];
    	//stores occurance of words
    	int count[]= new int[10000];
    	
    	WordCount word_count_object= new WordCount();
    	
		try
		{
			//loads txt file in scanner   
			Scanner sc  = new Scanner( new File("file.txt"));
			for(int i=0; i<10000; i++)
			{
				words[i]="";
				count[i]=0;
			}
			word_count_object.scanfile(sc, words, count);	
			sc.close();
		}
		//catches an exception if thrown by try block
		catch ( Exception e )
		{
			System.err.println("Something went wrong!");
		}
  }
}
