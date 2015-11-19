package ua.kostenko.mydictionary.UI.Fragments.Exercises.AdditionItems;

public class Exercise {
    private String mName;
    private int mPictureId;

    public Exercise(String mName, int mPictureId) {
        this.mName = mName;
        this.mPictureId = mPictureId;
    }

    public String getName() {
        return mName;
    }

    public int getPictureId() {
        return mPictureId;
    }
}
