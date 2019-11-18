package com.ams.utils;

public class KMP {
	//主串
	private char[] chiefString;
	//模式串
	private char[] schemaString;
	//next数组
	private int[] next;
	
	public KMP(char[] chiefString,char[] schemaString){
		this.chiefString = chiefString;
		this.schemaString = schemaString;
		this.setNext();
	}

	public char[] getChiefString() {
		return chiefString;
	}

	public void setChiefString(char[] chiefString) {
		this.chiefString = chiefString;
	}

	public char[] getSchemaString() {
		return schemaString;
	}

	public void setSchemaString(char[] schemaString) {
		this.schemaString = schemaString;
	}

	public int[] getNext() {
		return next;
	}

	public void setNext() {
		char[] schema = this.schemaString;
		next = new int[schema.length];
		next[0] = -1;
		int k = -1;
		for(int j = 1;j <= (schema.length-1);j++) {
			while(k > -1 && schema[k+1] != schema[j]) {
				k = next[k];
			}
			if(schema[k+1] == schema[j]) {
				k = k + 1;
			}
			next[j] = k;
		}
	}
	
	public int kmpMatch() {
		char[] chief = this.chiefString;
		char[] schema = this.schemaString;
		int[] next = this.getNext();
		int i = 0,j = 0;
		while(i < chief.length && j < schema.length) {
			if(j == -1 || chief[i] == schema[j]) {
				i++;
				j++;
			}
			else {
				j = next[j];
			}
		}
		if(j == schema.length)
			return i-j;
		else
			return -1;
	}
		
}


