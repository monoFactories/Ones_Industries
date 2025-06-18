package mod.base.modification.utils.lines;

public class LinesAction {
    public static String iterateName(String name) {
        if (name != null) {
            StringBuilder before = new StringBuilder();
            StringBuilder extension = new StringBuilder();
            char[] ch = name.toCharArray();
            boolean startWithPoint = false;
            for (int i = 0; i < ch.length; i++) {
                char c = ch[i];
                if (i == 0 && c == '.')
                    startWithPoint = true;
                if (c == '.') {
                    before.append(extension);
                    extension.setLength(0);
                    extension.append(".");
                } else {
                    extension.append(c);
                }
            }
            if (before.isEmpty() && !startWithPoint) {
                before.append(extension);
                extension.setLength(0);
            }
            char[] invertBefore = new StringBuilder(before).reverse().toString().toCharArray();
            StringBuilder bracket = new StringBuilder();
            StringBuilder nonBracket = new StringBuilder();
            boolean openBracket = false;
            long copyValue = 0;
            for (int i = 0; i < invertBefore.length; i++) {
                char c = invertBefore[i];
                if (i == 0) {
                    if (c == ')')
                        openBracket = true;
                    else {
                        nonBracket.append(invertBefore);
                        break;
                    }
                }
                if (openBracket) {
                    if (c == '(') {
                        openBracket = false;
                    }
                    bracket.append(c);
                } else {
                    nonBracket.append(c);
                }
            }
            boolean needBracketTest = true;
            if (openBracket) {
                nonBracket.append(bracket);
                bracket.setLength(0);
                needBracketTest = false;
            }
            nonBracket.reverse();
            if (needBracketTest) {
                bracket.reverse();
                if (bracket.length() < 2)
                    nonBracket.append(bracket);
                else {
                    String value = bracket.substring(1, bracket.length() - 1);
                    try {
                        copyValue = Long.parseLong(value);
                    } catch (NumberFormatException w) {
                        nonBracket.append('(').append(value).append(')');
                    }
                }
            }
            copyValue++;
            nonBracket.append('(').append(copyValue).append(')');
            nonBracket.append(extension);
            return nonBracket.toString();
        }
        return "(1)";
    }
}
