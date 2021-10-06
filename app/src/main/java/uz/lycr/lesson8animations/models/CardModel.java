package uz.lycr.lesson8animations.models;

public class CardModel {

    private int imgId;
    private int imgResourceId;

    public CardModel() {
    }

    public CardModel(int imgId, int imgResourceId) {
        this.imgId = imgId;
        this.imgResourceId = imgResourceId;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getImgResourceId() {
        return imgResourceId;
    }

    public void setImgResourceId(int imgResourceId) {
        this.imgResourceId = imgResourceId;
    }
}
