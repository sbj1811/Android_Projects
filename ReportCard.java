/**
 * Created by sjani on 2/11/2018.
 */

public class ReportCard {

    private String mStudentName;
    private String mSemester;
    private String mCourse1;
    private int mGrade1;
    private String mCourse2;
    private int mGrade2;
    private String mCourse3;
    private int mGrade3;
    private String letterGrade1;
    private String letterGrade2;
    private String letterGrade3;


    /**
     * ReportCard Constructor
     * @param StudentName
     * @param Semester
     * @param Course1
     * @param Grade1
     * @param Course2
     * @param Grade2
     * @param Course3
     * @param Grade3
     */

    public ReportCard (String StudentName,String Semester,String Course1,int Grade1,
                       String Course2,int Grade2,String Course3,int Grade3;){
        this.mStudentName = StudentName;
        this.mSemester = Semester;
        this.mCourse1 = Course1;
        this.mCourse2 = Course2;
        this.mCourse3 = Course3;
        this.mGrade1 = Grade1;
        this.mGrade2 = Grade2;
        this.mGrade3 = Grade3;

    }

    /**
     * Set Method
     * @param Name
     */

    public void setmStudentName (String Name){
        mStudentName = Name;
    }

    /**
     * Get Method
     * @return
     */

    public String getmStudentName (){
        return mStudentName;
    }

    public void setmSemester (String Semester){
        mSemester = Semester;
    }

    public String getmSemester (){
        return mSemester;
    }

    public void setmCourse1 (String Course1){
        mCourse1 = Course1;
    }

    public String getmCourse1 (){
        return mCourse1;
    }

    public void setmCourse2 (String Course2){
        mCourse2 = Course2;
    }

    public String getmCourse2 (){
        return mCourse2;
    }

    public void setmCourse3 (String Course3){
        mCourse3 = Course3;
    }

    public String getmCourse3 (){
        return mCourse3;
    }

    public void setmGrade1 (int Grade1){
        mGrade1 = Grade1;
        letterGrade1 = letterGrade(mGrade1);
    }

    public String getmGrade1 (){
        return mGrade1;
    }

    public void setmGrade2 (int Grade2){
        mGrade2 = Grade2;
        letterGrade2 = letterGrade(mGrade2);
    }

    public String getmGrade2 (){
        return mGrade2;
    }

    public void setmGrade3 (int Grade3){
        mGrade3 = Grade3;
        letterGrade3 = letterGrade(mGrade3);
    }

    public String getmGrade3 (){
        return mGrade3;
    }

    /**
     * Calulates Letter Grade level based on number grades
     * @param Grade
     * @return
     */

    public String letterGrade(int Grade){

        String LetterGrade;

        if(Grade >= 90 && Grade<=100) {
            LetterGrade = "A";
        } else if (Grade >= 80 && Grade <= 89) {
            LetterGrade = "B";
        } else if (Grade >= 70 && Grade <= 79) {
            LetterGrade = "C";
        } else if (Grade >= 50 && Grade <= 69) {
            LetterGrade = "D";
        } else {
            LetterGrade = "F";
        }

        return LetterGrade;
    }

    @Override
    public String toString() {
        return "Report Card:"+
                "\nName: " + mStudentName +
                "\nSemester: " + mSemester +
                "\nCourse 1: " + mCourse1 +
                "\nGrade: " + letterGrade1 +
                "\nCourse 2: " + mCourse2 +
                "\nGrade: " + letterGrade2 +
                "\nCourse 3: " + mCourse3 +
                "\nGrade: " + letterGrade3;
    }

}
