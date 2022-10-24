package main;

public abstract class TypKupowania {
    public void kupuj(Robotnik rob, Giełda giełda) {}

    public org.json.simple.JSONObject toJSONObject() {
        return new org.json.simple.JSONObject();
    }
}
