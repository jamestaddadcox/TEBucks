package com.techelevator.tebucks.exception;

public class TransferException extends RuntimeException {
        public TransferException() {
            super();
        }

        public TransferException(String message) {
            super(message);
        }

        public TransferException(String message, Exception e) {
            super(message, e);
        }


}
