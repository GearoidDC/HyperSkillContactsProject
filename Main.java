package contacts;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Contacts implements Serializable {
        private static final long serialVersionUID = 1L;
        LocalDateTime  timeCreated;
        LocalDateTime  timeLastEdited;
        String name;
        final Boolean isPerson;
        private String number;
        private final Pattern numberPattern = Pattern.compile("([+]?\\(?[\\w]+\\)?([-\\s][\\w]{2,})*)|([+]?[\\w]+[-\\s]\\([\\w]{2,}\\)([-\\s][\\w]{2,})*)");


        Contacts(Boolean isPerson) {
            this.isPerson = isPerson;
            this.number = "";
            timeCreated = LocalDateTime.now();
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setNumber(String number) {
            if(checkNumber(number)){
                this.number = number;
            } else {
                this.number = "";
            }

        }

        public void setTimeLastEdited(){
            this.timeLastEdited = LocalDateTime.now();
        }

        public String getNumber() {
            if("".equals(number)){
                return "[no data]";
            } else {
                return number;
            }

        }

        public String getFields(){
            return "";
        }

        public String getField(String field){
            return "";
        }

        public void setField(String field, String value){

        }

        public boolean hasNumber() {
                return !number.isEmpty();
        }

        private boolean checkNumber(String number){
            Matcher numberMatch = numberPattern.matcher(number);
            return numberMatch.matches();
        }

    @Override
    public String toString() {
        return name;
    }
    public String getDetails() {
            return "he";
    }


}

class Person extends Contacts {
    String surname;
    String birthDate = "[no data]";
    String gender = "[no data]";

    Person (){
        super(true);
    }
    public void setBirthDate(String birthDate){
        if (checkBirthDate(birthDate)){
            this.birthDate = birthDate;
        } else {
            this.birthDate = "[no data]";
        }
    }
    public String getBirthDate(){
        if ("[no data]".equals(birthDate)){
            return "[no data]";
        } else {
            return birthDate;
        }
    }

    @Override
    public String getFields(){
        return "name, surname, birth, gender, number";
    }

    @Override
    public void setField(String field, String value){
        if("surname".equals(field)){
            surname = value;

        } else if("name".equals(field)){
            name = value;

        } else if("number".equals(field)){
            this.setNumber(value);

        } else if("birthDate".equals(field)){
            this.setBirthDate(value);

        } else if("gender".equals(field)){
            this.setGender(value);

        }

    }

    public void setGender(String gender){
        if (gender.equalsIgnoreCase("m") | gender.equalsIgnoreCase("f")){
            this.gender = gender;
        } else {
            this.gender = "[no data]";
        }
    }

    public String getGender(){
        if ("[no data]".equals(gender)){
            return "[no data]";
        } else {
            return gender;
        }
    }

    public boolean checkBirthDate(String birthDate){
        return birthDate.matches("[0-9]{4}-([0][1-9]|[1][0-2])-[0-3][0-9]");
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    @Override
    public String getDetails(){
        return "Name: " + name + "\n"
                + "Surname: " + surname + "\n"
                + "Birth date: " +  getBirthDate() + "\n"
                + "Gender: " + getGender() + "\n"
                + "Number: " + getNumber() + "\n"
                + "Time created: " + timeCreated + "\n"
                + "Time last edit: " + timeLastEdited;

    }

    @Override
    public String toString() {

        return name + " " +  surname;

    }
}

class Organization  extends Contacts {

    private String address;


    Organization() {
        super(false);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    public String getDetails() {
        return "Organization name: " + name + "\n"
                + "Address: " + address + "\n"
                + "Number: " + getNumber() + "\n"
                + "Time created: " + timeCreated + "\n"
                + "Time last edit: " + timeLastEdited;

    }

    @Override
    public String getFields(){
        return "name, address, number";
    }

    @Override
    public void setField(String field, String value){
        if("address".equals(field)){
            address = value;

        } else if("name".equals(field)){
            name = value;

        } else if("number".equals(field)){
            this.setNumber(value);

        }

    }

    @Override
    public String toString() {

        return name;

    }
}

class SerializationUtils {
    /**
     * Serialize the given object to the file
     */
    public static void serialize(Object obj, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    /**
     * Deserialize to an object from the file
     */
    public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }
}

class menu {

    public int count(List<Contacts> contacts){
        return contacts.size();
    }

}

public class Main {
    
    
    
    
    public static void main(String[] args) {


        String fileName = "phonebook.db";
        List<Contacts> contacts;
        try {
            contacts = (List<Contacts> ) SerializationUtils.deserialize(fileName);
            System.out.println("open phonebook.db");
            System.out.println("");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            contacts = new ArrayList<>();
        }


        Scanner scanner = new Scanner(System.in);
        String name, surname, number, command, type, birthDate, gender,address;


        while (true){
            System.out.print("[menu] Enter action (add, list, search,count, exit): ");
            command = scanner.nextLine();

            if("count".equals(command)){
                System.out.println("The Phone Book has " + contacts.size() + " records.");
            } else if("search".equals(command)){
                while(true){
                    System.out.print("Enter search query: ");
                    String query = scanner.nextLine();
                    int counter = 1;
                    for(Contacts contact : contacts ){

                        if (contact.toString().toLowerCase().contains(query.toLowerCase()) | contact.getNumber().contains(query) ){
                            System.out.println(counter + ". " + contact);
                            counter++;
                        }



                    }
                    System.out.println("");
                    System.out.print("[search] Enter action ([number], back, again): ");
                    command = scanner.nextLine();
                    if("back".equals(command)){
                        break;
                    } else if ("again".equals(command)){

                    } else {
                        int index = Integer.parseInt(command);
                        System.out.println(contacts.get(index-1).getDetails());
                        System.out.println("");

                        System.out.print("[record] Enter action (edit, delete, menu): ");
                        command = scanner.nextLine();

                        if("edit".equals(command)){
                            System.out.print("Select a field (" + contacts.get(index-1).getFields() + "): ");
                            String field = scanner.nextLine();
                            System.out.print("Enter " + field + ":"  );
                            String value = scanner.nextLine();
                            contacts.get(index-1).setField(field, value);
                            System.out.println("Saved");
                            System.out.println(contacts.get(index-1).getDetails());
                        } else if ("menu".equals(command)){
                            break;
                        } else if ("delete".equals(command)){
                            contacts.remove(index-1);
                            System.out.println("The record removed!");
                        }
                    }
                }

            }

            else if("add".equals(command)){
                System.out.print("Enter the type (person, organization): ");
                type = scanner.nextLine();
                if("organization".equalsIgnoreCase(type)){
                    Organization organization = new Organization();
                    System.out.print("Enter the organization name: ");
                    name = scanner.nextLine();
                    organization.setName(name);


                    System.out.print("Enter the address: ");
                    address = scanner.nextLine();
                    organization.setAddress(address);

                    System.out.print("Enter the number: ");
                    number = scanner.nextLine();

                    organization.setNumber(number);
                    contacts.add(organization);
                    if(!organization.hasNumber()){
                        System.out.println("Wrong number format!");
                    }
                    organization.setTimeLastEdited();
                    System.out.println("A record created!");


                } else if ("person".equalsIgnoreCase(type)){
                    Person person = new Person();
                    System.out.print("Enter the name: ");
                    name = scanner.nextLine();
                    person.setName(name);


                    System.out.print("Enter the surname: ");
                    surname = scanner.nextLine();
                    person.setSurname(surname);

                    System.out.print("Enter the birth date: ");
                    birthDate = scanner.nextLine();
                    person.setBirthDate(birthDate);
                    if(!person.checkBirthDate(birthDate)){
                        System.out.println("Bad birth date!");
                    }



                    System.out.print("Enter the gender (M, F): ");
                    gender = scanner.nextLine();
                    person.setGender(gender);
                    if(!person.getGender().equals("[no data]")){
                        System.out.println("Bad gender!");
                    }


                    System.out.print("Enter the number: ");
                    number = scanner.nextLine();

                    person.setNumber(number);
                    contacts.add(person);
                    if(!person.hasNumber()){
                        System.out.println("Wrong number format!");
                    }
                    person.setTimeLastEdited();
                    System.out.println("A record created!");
                }

            }  else if("list".equals(command)){
                if (contacts.isEmpty()){
                    System.out.println("No records");
                } else {
                    int counter = 1;
                    for(Contacts contact : contacts ){
                        System.out.println(counter + ". " + contact);
                        counter++;
                    }
                    System.out.println("");

                    System.out.print("[list] Enter action ([number], back): ");
                    command = scanner.nextLine();
                    if("back".equals(command)){
                        break;
                    } else {
                        int index = Integer.parseInt(command);
                        System.out.println(contacts.get(index-1).getDetails());
                        System.out.println("");
                        while(true){
                        System.out.print("[record] Enter action (edit, delete, menu): ");
                        command = scanner.nextLine();

                        if("edit".equals(command)){
                            System.out.print("Select a field (" + contacts.get(index-1).getFields() + "): ");
                            String field = scanner.nextLine();
                            System.out.print("Enter " + field + ":"  );
                            String value = scanner.nextLine();
                            contacts.get(index-1).setField(field, value);
                            System.out.println("Saved");
                            System.out.println(contacts.get(index-1).getDetails());
                        } else if ("menu".equals(command)){
                            break;
                        } else if ("delete".equals(command)){
                            contacts.remove(index-1);
                            System.out.println("The record removed!");
                            break;
                        }
                        }
                    }

                }


            }  else if("exit".equals(command)){

                break;

            }
            System.out.println("");

        }

    }
    
    
}