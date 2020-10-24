package com.company;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Node {
    private String word;
    private int value;
    public Node next;

    public Node(String word){
        word = word.trim();
        this.word = word;
        int value = 0;
        for(int i = 0; i < word.length(); i++){
            value += word.charAt(i);
        }
        this.value = value;
    }

    public String getWord() {
        return word;
    }
    public int getValue() {
        return value;
    }
}
class List {
    public Node first;

    public void add(Node node) {
        Node current = first;
        Node prev = null;
        while (current != null && node.getValue() > current.getValue()) {
            prev = current;
            current = current.next;
        }
        if (prev == null)
            first = node;
        else
            prev.next = node;
        node.next = current;
    }

    public void delete(int value) {
        Node prev = null;
        Node current = first;
        while (current != null && value != current.getValue()) {
            prev = current;
            current = current.next;
        }
        if (prev == null)
            first = first.next;
        else
            prev.next = current.next;
    }

    public Node find(int value) {
        Node current = first;
        while (current != null && current.getValue() <= value) {
            if (current.getValue() == value)
                return current;
            current = current.next;
        }
        return null;
    }
    public void print(){
        Node current = first;
        while(current != null){
            System.out.print(current.getWord() + " ");
            current = current.next;
        }
    }
}
class HashTable {
    private List[] hashArray;
    private int size;

    public HashTable(int size) {
        this.size = size;
        this.hashArray = new List[size];
        for (int i = 0; i < size; i++) {
            hashArray[i] = new List();
        }
    }

    public int hashFunc(int value) {
        return value % size;
    }

    public void add(Node node) {
        int hashKey = hashFunc(node.getValue());
        hashArray[hashKey].add(node);
    }

    public void delete(String word) {
        int value = toValue(word);
        int hashKey = hashFunc(value);
        hashArray[hashKey].delete(value);
    }
    public Node find(String word) {
        int value = toValue(word);
        int hashKey = hashFunc(value);
        Node node = hashArray[hashKey].find(value);
        return node;
    }
    public int toValue(String word) {
        word = word.trim();
        int value = 0;
        for (int i = 0; i < word.length(); i++) {
            value += word.charAt(i);
        }
        return value;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.printf("(%d): ",i);
            hashArray[i].print();
            System.out.println();
        }

    }
}
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc =new Scanner(System.in);
        HashTable ht = new HashTable(30);
        FileReader fr = new FileReader("dictionary.txt");
        Scanner scan = new Scanner(fr);
        for (int i = 0; scan.hasNextLine(); i++) {
            ht.add(new Node(scan.nextLine()));
        }
        fr.close();
        ht.print();
        while(true){
            System.out.print("Введите 1, если Вы хотите удалить слово\n");
            System.out.print("Введите 2, если Вы хотите вставить слово\n");
            System.out.print("Введите 3, если Вы хотите произвести поиск\n");
            System.out.print("Введите 4, если Вы хотите просмотреть\n");
            int command =sc.nextInt();
            if(command==1){
                System.out.println("Введите слово,которое хотите удалить");
                String l=sc.next();
                try {
                    ht.delete(l);
                }catch(NullPointerException e){
                    System.out.println("Такого слова для удаления нет");
                }
            }
            if(command==2){
                System.out.println("Введите слово,которое хотите вставить");
                String n=sc.next();
                ht.add(new Node(n));
            }
            if(command==3){
                System.out.println("Введите слово,которое хотите найти");
                String m=sc.next();
                if(ht.find(m)==null) {
                    System.out.println("Не найдено");
                }else{
                    System.out.println("Найдено "+m);
                }
            }
            if(command==4){
                ht.print();
            }

        }
    }
}