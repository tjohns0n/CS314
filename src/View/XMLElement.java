/*
XMLElement abstract class
Super class of each element of an XML/SVG
Every element (<text>, <path>, etc) should extend this
*/

package View;

public abstract class XMLElement {
    // The opening tag of an element (e.g. <text font-size="12">)
    String start;
    // The ending tag of an element (e.g. </text>). Will equal "" if element has no ending tag
    String end;

    // Return the start tag
    public String getStart() {return start;}
    // Return the end tag
    public String getEnd() {return end;}

    public static class XMLVersion extends XMLElement {
        public XMLVersion() {
            start = "<?xml version=\"1.0\"?>";
            end = "";
        }
    }
}