package util


import util.XmlUtils

/**
 * xml compare class
 *
 */
public class CompareUtils {

    /**
     * compare by 2 folders path and their fileName
     *
     * @param folder1
     * @param folder2
     * @param fileName
     * @return
     */
    static def compare(String folder1, String folder2, String fileName) {

        def filePath1 = folder1 + "\\" + fileName;
        def filePath2 = folder2 + "\\" + fileName;

        compareBy2FileName(filePath1, filePath2);

    }

    /**
     * compare by 2 files path
     *
     * @param fileName1
     * @param fileName2
     */
    static def compareBy2FileName(String fileName1, String fileName2) {

        def excelMap = [:];
        def file1, file2;

        try {

            file1 = new File(fileName1);
            file2 = new File(fileName2);

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            println("error: file not found");

        }

        // notice replaceAll
        byte[] data2 = XmlUtils.outPut(file2.text.replaceAll("\n</", "</"));

        String file2format = new String(data2, "utf-8");

        String[] file2split = file2format.split("\n");

        //println(file2format)

        file1.eachWithIndex { String entry, int i ->

            def item1 = entry.trim();
            def item2 = file2split[i].trim();

            if (!item1?.equals(item2)) {

                excelMap.put(item1, item2);
            }
        }

        ExcelUtils.createExcel(fileName1, excelMap);
    }

    static void main(String[] args) {

        // expect
        def folder1 = "D:\\Dev\\test\\test1";

        // actual
        def folder2 = "D:\\Dev\\test\\test2";

        def fileName = "EDI2019051301404107-30.in_MB20190513094021rzAAyntn.xml"

        compare(folder1, folder2, fileName)


        //def fileName1 = "D:\\Dev\\test\\test1\\EDI2019051301404107-30.in_MB20190513094021rzAAyntn.xml";
        //def fileName2 = "D:\\Dev\\test\\test2\\EDI2019051301404107-30.in_MB20190513094021rzAAyntn.xml";
        //compareBy2FileName(fileName1, fileName2);


    }
}





