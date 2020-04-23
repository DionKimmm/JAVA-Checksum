import java.io.*;
import java.util.ArrayList;

public class Checksum {

    public int[] DoChecksum(ArrayList<Integer> checksumArrayList) {

        System.out.println("------계산 시작------");
        System.out.println(checksumArrayList);

        int lowByte = checksumArrayList.get(1) + checksumArrayList.get(3);
        int highByte = checksumArrayList.get(0) + checksumArrayList.get(2);


        System.out.print(highByte + " ");
        System.out.println(lowByte);
        if (lowByte > 255) {
            lowByte -= 256;
            highByte += 1;
        }

        if (highByte > 255) {
            highByte -= 256;
            highByte += 1;
        }

        if (lowByte > 255) {
            lowByte -= 256;
            highByte += 1;
        }

        if (highByte > 255) {
            highByte -= 256;
            highByte += 1;
        }

        System.out.print(highByte + " ");
        System.out.println(lowByte);

        String bin1 = Integer.toBinaryString(highByte);
        String bin2 = Integer.toBinaryString(lowByte);

        System.out.print("원값 : " + bin1 + " ");
        System.out.println(bin2);


//        String bin1 = Integer.toBinaryString(~highByte);
//        String bin2 = Integer.toBinaryString(~lowByte);
        bin1 = Integer.toBinaryString(~highByte);
        bin2 = Integer.toBinaryString(~lowByte);

        bin1 = bin1.substring(bin1.length()-8);
        bin2 = bin2.substring(bin2.length()-8);


        System.out.print(" 1의 보수 : " + bin1 + " ");
        System.out.println(bin2);

        int temp[] = new int[2];
        temp[0] = Integer.parseInt(bin1, 2);
        temp[1] = Integer.parseInt(bin2, 2);

        return temp;
    }

    public static void main(String[] args) {

        Checksum cs = new Checksum();

        try{
            //파일 객체 생성
            File file = new File("C:\\Users\\20143\\IdeaProjects\\Checksum\\input.txt");
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);

            int character = 0;
            int count = 0; // even parity bit를 위한 카운트 변수
            int parityBit = 0;
            boolean hasResidue = false; // checksum 계산시 16bit로 떨어지지 않는 데이터 처리를 위한 변수
            ArrayList<Integer> checksumArrayList = new ArrayList<>();

            // 1. filereader.read() 메소드로 한 문자씩 read 합니다. (타입 : ASCII 값이 담긴 Int)
            // 2. Integer.toBinaryString() 메소드로 String으로 형변환 합니다. (타입 : ASCII Int값의 2진수 String)
            // 3. 인덱스값으로 계산을 처리하기 위해 toCharArray() 메소드로 형변환 합니다. (타입 : 2진수 Char[])
            // 4. for문으로 인덱스로 접근하여 2진수에 1이 홀수 개인지 짝수개인지 파악하여 parity bit를 오른쪽에 삽입합니다.
            // 5. 16비트를 단위로 checksum 계산을 한다.
            // 5-1. 16비트로 묶는다
            // 5-2. 16비트끼리 이진수 덧셈한다.
            // 5-3. 캐리를 처리한다.
            // 5-4. 덧셈 결과를 1의 보수 처리한다.
            // 6. 결과 값을 output_checksum.txt에 출력한다.

            // 1
            while((character = filereader.read()) != -1){
                count = 0;
                System.out.print((char)character + " ");

                // 2
                System.out.print("변환 전: " + character+"\t");
                String binaryString = Integer.toBinaryString(character);

                // ASCII 코드가 7비트가 아닐경우 왼쪽에 0을 추가하며 7비트 자릿수를 맞춰줍니다.
                while (binaryString.length() != 7) {
                    binaryString = "0" + binaryString;
                }

                System.out.println(binaryString);
                // 3
                char[] binaryCharArray = binaryString.toCharArray();
//                System.out.println(binaryCharArray);
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
                    System.out.print("짝수 패리티 비트 적용: ");
                } else {
                    parityBit = 1;
                    System.out.print("짝수 패리티 비트 적용: ");
                }

                binaryString += parityBit; // String 타입의 even parity bit가 포함된 2진수 8bit로 변환됨.
                System.out.println(binaryString);

                // 5
                // 5-1. 16비트로 묶는다
                // 5-2. 16비트끼리 이진수 덧셈한다.
                // 5-3. 캐리를 처리한다.
                // 5-4. 덧셈 결과를 1의 보수 처리한다.



                int binToInt = Integer.parseInt(binaryString, 2);
                System.out.println("변환 후 : " + binToInt + "\n");
                if (checksumArrayList.size() < 4){
                    checksumArrayList.add(binToInt);
                    System.out.println(checksumArrayList);
                } else { // 덧셈 연산 시작

                    int[] result;
                    result = cs.DoChecksum(checksumArrayList);
                    System.out.println("result: " + result[0] + " " + result[1]);

                    checksumArrayList.clear();
                    checksumArrayList.add(binToInt);
                    System.out.println(checksumArrayList);
                }

                // 6


            }
            filereader.close();

            // 16비트 계산을 위한 짝이 갖추어지지 않았을 때
            // 텍스트 문자 0의 아스키 코드에 짝수 패리티 비트가 더해진 8비트를 변환하면 96이라는 숫자가 된다.
            // 따라서 텍스트 파일에 96을 어레이리스트에 추가하면 0을 추가한 효과를 가지게 된다.
            if(!checksumArrayList.isEmpty()) {
                System.out.println("--떨이 처리 !! --");
                while(checksumArrayList.size() < 4) {
                    checksumArrayList.add(96);
                }
                int[] result;
                result = cs.DoChecksum(checksumArrayList);
                System.out.println("result: " + result[0] + " " + result[1]);

            }
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
