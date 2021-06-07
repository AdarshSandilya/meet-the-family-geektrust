package com.adarsh.geektrust;

public class AppConstants {

    private AppConstants() {
    }

    public static class Commands {

        private Commands() {
        }
        public static final String ADD_KING = "ADD_KING";
        public static final String GET_RELATIONSHIP = "GET_RELATIONSHIP";
        public static final String ADD_CHILD = "ADD_CHILD";
        public static final String ADD_SPOUSE = "ADD_SPOUSE";
    }

    public static class Message {

        private Message() {
        }

        public static final String ILLEGAL_ARGUMENT = "Name or gender can't be null";
        public static final String PERSON_NOT_FOUND = "PERSON_NOT_FOUND";
        public static final String CHILD_ADDITION_FAILED = "CHILD_ADDITION_FAILED";
        public static final String CHILD_ADDITION_SUCCEEDED = "CHILD_ADDITION_SUCCEEDED";
        public static final String NONE = "NONE";
    }

}