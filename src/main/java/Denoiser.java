import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class Denoiser {
    public static String denoise(String input) {
        Document doc = Jsoup.parse(input);
        doc.select("code").remove();


        return doc.text();
    }

    public static void main (String[] args) {
        String sample = "| <p>I couldn't resist - the other answers are undoubtedly true, but you really can't walk past the following code:</p>&#xA;&#xA;<p><div class=\"snippet\" data-lang=\"js\" data-hide=\"false\" data-console=\"true\" data-babel=\"false\">&#xD;&#xA;<div class=\"snippet-code\">&#xD;&#xA;<pre class=\"snippet-code-js lang-js prettyprint-override\"><code>var aﾠ = 1;&#xD;&#xA;var a = 2;&#xD;&#xA;var ﾠa = 3;&#xD;&#xA;if(aﾠ==1 &amp;&amp; a== 2 &amp;&amp;ﾠa==3) {&#xD;&#xA; console.log(\"Why hello there!\")&#xD;&#xA;}</code></pre>&#xD;&#xA;</div>&#xD;&#xA;</div>&#xD;&#xA;</p>&#xA;&#xA;<p>Note the weird spacing in the <code>if</code> statement (that I copied from your question). It is the half-width Hangul (that's Korean for those not familiar) which is an Unicode space character that is not interpreted by ECMA script as a space character - this means that it is a valid character for an identifier. Therefore there are three completely different variables, one with the Hangul after the a, one with it before and the last one with just a. Replacing the space with <code>_</code> for readability, the same code would look like this:</p>&#xA;&#xA;<p><div class=\"snippet\" data-lang=\"js\" data-hide=\"false\" data-console=\"true\" data-babel=\"false\">&#xD;&#xA;<div class=\"snippet-code\">&#xD;&#xA;<pre class=\"snippet-code-js lang-js prettyprint-override\"><code>var a_ = 1;&#xD;&#xA;var a = 2;&#xD;&#xA;var _a = 3;&#xD;&#xA;if(a_==1 &amp;&amp; a== 2 &amp;&amp;_a==3) {&#xD;&#xA; console.log(\"Why hello there!\")&#xD;&#xA;}</code></pre>&#xD;&#xA;</div>&#xD;&#xA;</div>&#xD;&#xA;</p>&#xA;&#xA;<p>Check out <a href=\"https://mothereff.in/js-variables#%EF%BE%A0%E1%85%A0%E1%85%9F\" rel=\"noreferrer\">the validation on Mathias' variable name validator</a>. If that weird spacing was actually included in their question, I feel sure that it's a hint for this kind of answer.</p>&#xA;&#xA;<p>Don't do this. Seriously.</p>&#xA;&#xA;<p>Edit: It has come to my attention that (although not allowed to start a variable) the <a href=\"https://en.wikipedia.org/wiki/Zero-width_joiner\" rel=\"noreferrer\">Zero-width joiner</a> and <a href=\"https://en.wikipedia.org/wiki/Zero-width_non-joiner\" rel=\"noreferrer\">Zero-width non-joiner</a> characters are also permitted in variable names - see <a href=\"https://reverseengineering.stackexchange.com/q/53\">Obfuscating JavaScript with zero-width characters - pros and cons?</a>.</p>&#xA;&#xA;<p>This would look like the following:</p>&#xA;&#xA;<p><div class=\"snippet\" data-lang=\"js\" data-hide=\"false\" data-console=\"true\" data-babel=\"false\">&#xD;&#xA;<div class=\"snippet-code\">&#xD;&#xA;<pre class=\"snippet-code-js lang-js prettyprint-override\"><code>var a= 1;&#xD;&#xA;var a\u200D= 2; //one zero-width character&#xD;&#xA;var a\u200D\u200D= 3; //two zero-width characters (or you can use the other one)&#xD;&#xA;if(a==1&amp;&amp;a\u200D==2&amp;&amp;a\u200D\u200D==3) {&#xD;&#xA; console.log(\"Why hello there!\")&#xD;&#xA;}</code></pre>&#xD;&#xA;</div>&#xD;&#xA;</div>&#xD;&#xA;</p>&#xA; ";
        Document doc = Jsoup.parse(sample);
        doc.select("code").remove();
        doc.select("lang").remove();
        String output = doc.text();
        System.out.println(output);

        //System.out.println(Jsoup.clean(sample, Whitelist.basic()).toString());

        //System.out.println(denoise (sample));
    }
}
