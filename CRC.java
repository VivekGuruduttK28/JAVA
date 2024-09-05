package crc;
import java.io.*;
import java.util.*;
public class CRC {

	public static void main(String[] args) {
	Scanner sc=new Scanner(System.in);
	int[] data;
	int[] div;
	int[] rem;
	int[] crc;
	int[] divisor;
	int data_bits,divisor_bits,tot_length;
	System.out.println("Enter the no of data bits");
	data_bits=sc.nextInt();
	data = new int[data_bits];
	System.out.println("Enter the data");
	for(int i=0;i<data_bits;i++)
		data[i]=sc.nextInt();
	System.out.println("Enter the no of divisor bits");
	divisor_bits=sc.nextInt();
	System.out.println("Enter the divisor bits");
	divisor=new int[divisor_bits];
	for(int i=0;i<divisor_bits;i++)
		divisor[i]=sc.nextInt();
	System.out.println("Data bits are");
	for(int i=0;i<data_bits;i++)
		System.out.print(data[i]);
	System.out.println();
	System.out.println("Divisor bits are");
	for(int i=0;i<divisor_bits;i++)
		System.out.print(divisor[i]);
	System.out.println();
	tot_length=data_bits+divisor_bits-1;
	div=new int[tot_length];
	crc=new int[tot_length];
	rem=new int[tot_length];
	for(int i=0;i<data_bits;i++)
		div[i]=data[i];
	System.out.println("Dividend after appending 0s are ");
	for(int i=0;i<tot_length;i++)
		System.out.print(div[i]);
	System.out.println();
	for(int i=0;i<tot_length;i++)
		rem[i]=div[i];
	rem=divide(div,divisor,rem);
	for(int i=0;i<div.length;i++)
		crc[i]=div[i]^rem[i];
	System.out.println();
	System.out.println("CRC code is");
	for(int i=0;i<crc.length;i++)
		System.out.print(crc[i]);
	System.out.println();
	System.out.println("Enter the CRC code of "+tot_length+" bits");
	for(int i=0;i<crc.length;i++)
		crc[i]=sc.nextInt();
	for(int i=0;i<crc.length;i++)
		rem[i]=crc[i];
	rem=divide(crc,divisor,rem);
	for(int i=0;i<rem.length;i++) {
		if(rem[i]!=0) {
			System.out.println("Error");
			break;
		}
		if(i==rem.length-1)
			System.out.println("No error");
	}
	
	}
	static int[] divide(int[] div,int[] divisor,int[] rem) {
		int cur=0;
		while(true) {
		for(int i=0;i<divisor.length;i++) 
			rem[cur+i]=rem[cur+i]^divisor[i];
			while(rem[cur]==0 && cur!=rem.length-1)
				cur++;
			if((rem.length-cur)<divisor.length)
				break;
			
		}
		return rem;
	}

}