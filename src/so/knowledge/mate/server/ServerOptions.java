
/**
 * Created by Sun on 15/9/20.
 */

package so.knowledge.mate.server;


import se.lth.cs.srl.options.FullPipelineOptions;



public class ServerOptions extends FullPipelineOptions {

    public int sentenceMaxLength = -1;
    public int port = 5900;

    public ServerOptions() {
    }

    public String getSubUsageOptions() {
        return "-port   <int>     the port to use (default 8081)";
    }

    public int trySubParseArg(String[] args, int ai) {
        if(args[ai].equals("-port")) {
            ++ai;
            this.port = Integer.valueOf(args[ai]).intValue();
            ++ai;
        } else if(args[ai].equals("-maxLength")) {
            ++ai;
            this.sentenceMaxLength = Integer.parseInt(args[ai]);
            ++ai;
        }  else if(args[ai].equals("-nullLS")) {
            ++ai;
            FullPipelineOptions.NULL_LANGUAGE_NAME = args[ai];
            ++ai;
        }

        return ai;
    }

    public Class<?> getIntendedEntryClass() {
        return Server.class;
    }
}

