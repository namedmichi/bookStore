package de.mst.chatgpt;

import java.util.List;

public class ChatResponse {

    public ChatResponse() {
        super();
    }

    public ChatResponse(List<Choice> choices) {
        super();
        this.choices = choices;
    }

    private List<Choice> choices;

    // constructors, getters and setters

    public static class Choice {

        public Choice() {
            super();
        }

        public Choice(int index, Message message) {
            super();
            this.index = index;
            this.message = message;
        }

        private int index;
        private Message message;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
        // constructors, getters and setters
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
