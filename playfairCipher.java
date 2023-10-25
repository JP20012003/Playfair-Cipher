import java.awt.Point;
class playfairCipher {
private static char[][] charTable;
private static Point[] positions;
private static String prepareText(String s, boolean chgJtoI) {
s = s.toUpperCase().replaceAll(&quot;[^A-Z]&quot;, &quot;&quot;);
return chgJtoI ? s.replace(&quot;J&quot;, &quot;I&quot;) : s.replace(&quot;Q&quot;, &quot;&quot;);
}
private static void createTbl(String key, boolean chgJtoI) {
charTable = new char[5][5];
positions = new Point[26];
String s = prepareText(key + &quot;ABCDEFGHIJKLMNOPQRSTUVWXYZ&quot;,chgJtoI);
int len = s.length();
for (int i = 0, k = 0; i &lt; len; i++) {
char c = s.charAt(i);

if (positions[c - &#39;A&#39;] == null) {
charTable[k / 5][k % 5] = c;
positions[c - &#39;A&#39;] = new Point(k % 5, k / 5);
k++;
}
}
}
private static String codec(StringBuilder txt, int dir) {
int len = txt.length();
for (int i = 0; i &lt; len; i += 2) {
char a = txt.charAt(i);
char b = txt.charAt(i + 1);
int row1 = positions[a - &#39;A&#39;].y;

int row2 = positions[b - &#39;A&#39;].y;
int col1 = positions[a - &#39;A&#39;].x;
int col2 = positions[b - &#39;A&#39;].x;
if (row1 == row2) {
col1 = (col1 + dir) % 5;
col2 = (col2 + dir) % 5;
} else if (col1 == col2) {
row1 = (row1 + dir) % 5;
row2 = (row2 + dir) % 5;
} else {
int tmp = col1;
col1 = col2;
col2 = tmp;
}
txt.setCharAt(i, charTable[row1][col1]);
txt.setCharAt(i + 1, charTable[row2][col2]);
}
return txt.toString();
}
private static String encode(String s) {
StringBuilder sb = new StringBuilder(s);
for (int i = 0; i &lt; sb.length(); i += 2) {
if (i == sb.length() - 1) {

sb.append(sb.length() % 2 == 1 ? &#39;X&#39; : &quot;&quot;);
} else if (sb.charAt(i) == sb.charAt(i + 1)) {
sb.insert(i + 1, &#39;X&#39;);
}
}
return codec(sb, 1);
}

private static String decode(String s) {
return codec(new StringBuilder(s), 4);
}
public static void main(String[] args) throws java.lang.Exception {
String key = &quot;CSE&quot;;
String txt = &quot;Security Lab&quot;; /* make sure string length is even */ /* change J
to I */
boolean chgJtoI = true;
createTbl(key, chgJtoI);
String enc = encode(prepareText(txt, chgJtoI));
System.out.println(&quot;Simulating Playfair Cipher\n----------------------&quot;);
System.out.println(&quot;Input Message :&quot; + txt);
System.out.println(&quot;Encrypted Message :&quot; + enc);
System.out.println(&quot;Decrypted Message :&quot; + decode(enc));
}
}