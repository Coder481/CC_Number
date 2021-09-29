package com.hrithik.ccnumber;

public class CheckForCreditCardNumber {

    public boolean isCreditCardNumberValid(String cardNum) {

        return isLuhnAlgoApplied(cardNum) && (
                isCardNumberInAmericanExpressFormat(cardNum)
                        || isCardNumberInDiscoverFormat(cardNum)
                        || isCardNumberInMasterCardFormat(cardNum)
                        || isCardNumberInVisaFormat(cardNum));
    }

    // check that Luhn Algorithm is applied or not
    private boolean isLuhnAlgoApplied(String cardNum) {

        String spaceRemovedStr = cardNum.replaceAll("\\s","");

        int length = spaceRemovedStr.length();
        boolean isSecond = false;

        int sum = 0;

        for (int i=length-1; i>=0; i--){

            int ele = Integer.parseInt(String.valueOf(spaceRemovedStr.charAt(i)));

            if (isSecond) ele = ele * 2;

            if (ele > 9){
                int first = ele / 10;
                int second = ele % 10;
                ele = first + second;
            }

            sum += ele;

            isSecond = !isSecond;

        }

        return sum % 10 == 0;
    }

    // check for card number in Visa Format
    private boolean isCardNumberInVisaFormat(String cardNum) {

        String[] ccNumArr = cardNum.split(" ");

        int allowedDigits = cardNum.replaceAll("\\s","").length();

        if (allowedDigits == 16
                && ccNumArr.length == 4
                && ccNumArr[0].length() == 4
                && ccNumArr[1].length() == 4
                && ccNumArr[2].length() == 4
                && ccNumArr[3].length() == 4
                && cardNum.startsWith("4")) return true;

        return allowedDigits > 12 && allowedDigits < 20 && cardNum.startsWith("4");
    }

    // check for card number in Master Card Format
    private boolean isCardNumberInMasterCardFormat(String cardNum) {

        String[] ccNumArr = cardNum.split(" ");

        String firstTwo = "";
        String firstSix = "";

        if (ccNumArr.length == 1 && cardNum.length() == 16){
            firstTwo = cardNum.substring(0,2);
            firstSix = cardNum.substring(0,6);

            int firstTwoInt = Integer.parseInt(firstTwo);
            int firstSixInt = Integer.parseInt(firstSix);

            boolean inRange = (firstTwoInt > 51 - 1 && firstTwoInt < 55 + 1)
                    || (firstSixInt > 222100 - 1 && firstSixInt < 272099 + 1);

            return inRange;
        }
        else if (ccNumArr.length == 4
                && ccNumArr[0].length() == 4
                && ccNumArr[1].length() == 4
                && ccNumArr[2].length() == 4
                && ccNumArr[3].length() == 4){

            String firstFour = ccNumArr[0];
            String nextTwo = ccNumArr[1].substring(0,2);

            firstTwo = firstFour.substring(0,2);
            firstSix = firstFour + nextTwo;

            int firstTwoInt = Integer.parseInt(firstTwo);
            int firstSixInt = Integer.parseInt(firstSix);

            boolean inRange = (firstTwoInt > 51 - 1 && firstTwoInt < 55 + 1)
                    || (firstSixInt > 222100 - 1 && firstSixInt < 272099 + 1);

            return inRange;
        }else {
            return false;
        }

    }

    // check for card number in Discover Format -> XXXX XXXX XXXX XXXX
    private boolean isCardNumberInDiscoverFormat(String cardNum) {

        String[] ccNumArr = cardNum.split(" ");

        String firstThree = "";
        String firstSix = "";

        if (ccNumArr.length == 1 && cardNum.length() == 16){
            firstThree = cardNum.substring(0,3);
            firstSix = cardNum.substring(0,6);

            int firstSixInt = Integer.parseInt(firstSix);
            int firstThreeInt = Integer.parseInt(firstThree);

            boolean inRange = ( firstSixInt > 622126 - 1 && firstSixInt < 622925 + 1)
                    || (firstThreeInt > 644 - 1 && firstThreeInt < 649 + 1)
                    || cardNum.startsWith("6011")
                    || cardNum.startsWith("65");

            return inRange;
        }
        else if (ccNumArr.length == 4
                && ccNumArr[0].length() == 4
                && ccNumArr[1].length() == 4
                && ccNumArr[2].length() == 4
                && ccNumArr[3].length() == 4){

            String firstFour = ccNumArr[0];
            String nextTwo = ccNumArr[1].substring(0,2);

            firstThree = firstFour.substring(0,3);
            firstSix = firstFour + nextTwo;

            int firstSixInt = Integer.parseInt(firstSix);
            int firstThreeInt = Integer.parseInt(firstThree);

            boolean inRange = ( firstSixInt > 622126 - 1 && firstSixInt < 622925 + 1)
                    || (firstThreeInt > 644 - 1 && firstThreeInt < 649 + 1)
                    || cardNum.startsWith("6011")
                    || cardNum.startsWith("65");

            return inRange;
        }else {
            return false;
        }

    }

    // check for card number in American Express Format -> XXXX XXXXXX XXXXX
    public boolean isCardNumberInAmericanExpressFormat(String cardNum) {

        String[] ccNumArr = cardNum.split(" ");

        if (ccNumArr.length == 1
                && (cardNum.startsWith("34") || cardNum.startsWith("37"))
                && cardNum.length() == 15){
            return true;
        }
        else return ccNumArr.length == 3
                && ccNumArr[0].length() == 4
                && ccNumArr[1].length() == 6
                && ccNumArr[2].length() == 5
                && (cardNum.startsWith("34") || cardNum.startsWith("37"));
    }

}
