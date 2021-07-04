/*
This program uses 3 hashmaps to Map corresponding values. A class Vehicle has been used as user defined data type to store 2 parameter at once. Using HashMaps reduced the number of iteration that is needed to search a particular value associated with some other value.



*/

import java.util.*;
import java.io.File;


//Class used as user defined data type to contain two types of values
 class Vehicle
  {
     String rno="";
     int sno=-1;
    public Vehicle(String rno, int sno)
    {
      this.rno=rno;
      this.sno=sno;
    }
  }


class Main {
  public static void main(String[] args) {
    readFromFile();
  }

   
  static boolean arr[];//array which stores whether the slot is vacant or not
  static HashMap<Integer,ArrayList<Vehicle>> agetoVehicle=new HashMap<Integer,ArrayList<Vehicle>>();//a Map having Age as key and an ArrayList of type vehicle as Value.
  static HashMap<String,Integer> snotorno=new HashMap<String,Integer>();//A map having Slot no. as key and Registration no. as value
  static HashMap<Integer,Integer> snotoage=new HashMap<Integer,Integer>(); // A map having Slot no. as key and Age as Value
  static int numberofslots;


  //a method which reads from the file and calls other methods as per the lines in the file.
  public static void readFromFile()
  {
    try{

        File file=new File("input.txt");
        Scanner y = new Scanner(file);
        int f=0;
        while(y.hasNextLine())
        {
          //reads the first line to get the number of slots
          if(f==0)
          {
            
            numberofslots=Integer.parseInt(y.nextLine().substring(19));
            createParkingLot(numberofslots);
            f=1;
            arr=new boolean[numberofslots];
          }

          //reads 2nd line onwards
          else 
          {
            String inputLine=y.nextLine();

            //if the line is for Parking a new vehicle
            if(inputLine.substring(0,1).equals("P"))
            {
              int index=-1;
              String rno=inputLine.substring(5,19);
              int age= Integer.parseInt(inputLine.substring(30));
              for(int i=0;i<numberofslots;i++)
              {
                if(arr[i]==false)
                {
                  index=i;
                  break; 
                }
              }
              createTicket(rno,age,index);
            }
            
          
          //if the line is to get the slot number
          else if(inputLine.substring(0,1).equals("S") && inputLine.charAt(11)=='s')
            {
              getSlotNoByAge(Integer.parseInt(inputLine.substring(31)));
            }

            //if the line is to get the vehicle registration number for driver of a given age
            else if(inputLine.substring(0,1).equals("V"))
            {
              getRegNoByAge(Integer.parseInt(inputLine.substring(47)));
            }

            //if the line is to get the slot number of a vehicle with given registration number
            else if(inputLine.substring(0,1).equals("S") && inputLine.charAt(11)!='S')
            {
              getSnoByRno(inputLine.substring(31));
              
            }


            //if the line describes leaving of a car
            else{
              leave(Integer.parseInt(inputLine.substring(6))-1);
            }

          }
          }
        }

    catch(Exception e){return;}


  }

  //outputs the total number of slots in the parking lot
    public static void createParkingLot(int numberofslots)
    {
      System.out.println("Created parking of "+numberofslots+" slots");
    }

  //function to create the ticket and assign slots after getting the registration number and age of the driver
    public static void createTicket(String s, int age, int slot)
    {
        
        if(!agetoVehicle.containsKey(age))
        {
          Vehicle v=new Vehicle(s,slot);
          ArrayList<Vehicle> al= new ArrayList<Vehicle>();
          al.add(v);
          agetoVehicle.put(age,al);
        }
        else
        {
          Vehicle v=new Vehicle(s,slot);
          agetoVehicle.get(age).add(v);
        }
        arr[slot]=true;

        System.out.println("Car with vehicle registration number "+ s +" has been parked at slot number "+ (slot+1));
        snotorno.put(s,slot);
        snotoage.put(slot,age);

    }


    //a function which outputs the slots by age y accessing the map
    public static void getSlotNoByAge(int age)
    {
      ArrayList<Vehicle> lv=new ArrayList<Vehicle>();
      lv=agetoVehicle.get(age);
      
      for(int i=0;i<lv.size();i++)
      { 
        System.out.print(lv.get(i).sno);
        if(i!=(lv.size()-1))
        System.out.print(",");
      }
      System.out.println();
    }

    //function which outputs the Registration number by age by accessing the map
    public static void getRegNoByAge(int age)
    {
      ArrayList<Vehicle> lv=new ArrayList<Vehicle>();
      lv=agetoVehicle.get(age);
      
      for(int i=0;i<lv.size();i++)
      { 
        System.out.print(lv.get(i).rno);
        if(i!=(lv.size()-1))
        System.out.print(",");
      }
      

    }

    //outputs Slot number associated with the asked Regis. number
    public static void getSnoByRno(String s)
    {
      System.out.println(snotorno.get(s));
    }


    //function which vacates the slot, removes the regis. number, age and other details from the map and outputs which slot have been vacated by which vehicle driven by driver of what age
    public static void leave(int slot)
    {
        int age=snotoage.get(slot);
        arr[slot]=false;
        String regno="";
        //String regno=agetoVehicle.get(age).rno;
        for(int i=0;i<agetoVehicle.get(age).size();i++)
        {
          if(agetoVehicle.get(age).get(i).sno==slot)
          { 
            regno=agetoVehicle.get(age).get(i).rno;
            agetoVehicle.get(age).remove(i);
          }
        }
        snotoage.remove(slot);
        System.out.println("Slot number "+(slot+1)+" vacated, the car with vehicle registration number "+regno+" left the space, the driver of the car was of age "+age);
    }




}

