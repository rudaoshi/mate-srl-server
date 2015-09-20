package so.knowledge.mate.server;



import se.lth.cs.srl.CompletePipeline;
import se.lth.cs.srl.corpus.Sentence;

import se.lth.cs.srl.util.FileExistenceVerifier;


import org.zeromq.ZMQ;

/**
 * Crude ZeroMQ server for Stanford CoreNLP.
 * The choice of so.knowledge.corenlp.server is really incidental/arbitrary here.
 * The server accepts strings and emits XML containing their parse.
 *
 * Public domain (Eric Kow)
 */
public class Server  {



    public Server() {

    }


    private static void handleMessage(CompletePipeline pipeline,
                                      Connection connection,
                                      final String message)
            throws Exception
    {


        Sentence sen=pipeline.parse(message);

        connection.reply(sen.toString().getBytes("utf8"));

    }


    private static void runServer(final ServerOptions options) throws Exception {

        CompletePipeline completePipeline = CompletePipeline.getCompletePipeline(options);

        int port = options.port;

        ZMQ.Context context = ZMQ.context(1);

        //  Socket to talk to clients
        ZMQ.Socket responder = context.socket(ZMQ.REP);
        responder.bind("tcp://*:" + port);
        final Connection connection = new Connection(context, responder);

        while (!(Thread.currentThread().isInterrupted()
                || connection.isStopRequested())) {
            // Wait for next request from the client
            byte[] request = responder.recv(0);

            handleMessage(completePipeline, connection, new String(request, "UTF-8"));
        }
        responder.close();
        context.term();
    }


    public static void main(String[] args) throws Exception {


        // this was taken directly from edu.stanford.nlp.pipeline.StanfordCoreNLP.main
        ServerOptions options=new ServerOptions();
        if (args.length > 0) {

            options.parseCmdLineArgs(args);
            String error=FileExistenceVerifier.verifyCompletePipelineAllNecessaryModelFiles(options);
            if(error!=null){
                System.err.println(error);
                System.err.println("Aborting.");
                System.exit(1);
            }


        }

        runServer(options);
    }
}
