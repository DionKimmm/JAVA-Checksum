import java.io.*;

public class Checksum {

    public static void main(String[] args) {

        try{
            //파일 객체 생성
            File file = new File("C:\\Users\\20143\\IdeaProjects\\Checksum\\input.txt");
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);

            int character = 0;
            int count = 0; // even parity bit를 위한 카운트 변수
            int parityBit = 0;
            boolean hasResidue = false; // checksum 계산시 16bit로 떨어지지 않는 데이터 처리를 위한 변수

            // 1. filereader.read() 메소드로 한 문자씩 read 합니다. (타입 : ASCII 값이 담긴 Int)
            // 2. Integer.toBinaryString() 메소드로 String으로 형변환 합니다. (타입 : ASCII Int값의 2진수 String)
            // 3. 인덱스값으로 계산을 처리하기 위해 toCharArray() 메소드로 형변환 합니다. (타입 : 2진수 Char[])
            // 4. for문으로 인덱스로 접근하여 2진수에 1이 홀수 개인지 짝수개인지 파악하여 parity bit를 오른쪽에 삽입합니다.
            // 5. 16비트를 단위로 checksum 계산을 한다.
            // 6. 결과 값을 output_checksum.txt에 출력한다.

            // 1
            while((character = filereader.read()) != -1){
                count = 0;
//                System.out.print((char)character);

                // 2
                System.out.print(character+"\t");
                String binaryString = Integer.toBinaryString(character);

                // ASCII 코드가 7비트가 아닐경우 왼쪽에 0을 추가하며 7비트 자릿수를 맞춰줍니다.
                while (binaryString.length() != 7) {
                    binaryString = "0" + binaryString;
                }

                System.out.println(binaryString);
                // 3
                char[] binaryCharArray = binaryString.toCharArray();
                System.out.println(binaryCharArray);
                // 4
                for(int i = 0; i < binaryString.length(); i++){
//                    System.out.println(binaryCharArray[i]);
                    if (binaryCharArray[i] == '1') {
                        count++;
//                        System.out.println(count);
                    }
                }
                System.out.println("count : " + count);
                if (count % 2 == 0) {
                    parityBit = 0;
                } else {
                    parityBit = 1;
                }

                binaryString += parityBit; // String 타입의 even parity bit가 포함된 2진수 8bit로 변환됨.
                System.out.println(binaryString+"\n");

                // 5


                int binToInt = Integer.parseInt(binaryString, 2);
                // 6


            }
            filereader.close();
        }catch (IOException e) {

        }

//        // input.txt 파일을 읽습니다.
//        File dir = new File("C:\\Users\\20143\\IdeaProjects\\Checksum\\input.txt");
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(dir));
//            while(true) {
//                String line = br.readLine();
//                if(line.length()!=0) {
//                    System.out.println(line);
//                    if (line==null) break;
//                }
//            }
//            br.close();
//        } catch (NullPointerException | IOException e) {
//
//        }
    }
}
//    public void read() {
//        File dir = new File("C:\\Users\\20143\\IdeaProjects\\Checksum\\input.txt");
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(dir));
//            while(true) {
//                String line = br.readLine();
//                if(line.length()!=0) {
//                    System.out.println(line);
//                    if (line==null) break;
//                }
//            }
//            br.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
