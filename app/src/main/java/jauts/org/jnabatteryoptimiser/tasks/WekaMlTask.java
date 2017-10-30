package jauts.org.jnabatteryoptimiser.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 * Created by liangze on 30/10/17.
 */

public class WekaMlTask extends AsyncTask<Void, Void, Void> {

    public static final String FILE_URL = "power.arff";

    private Context mContext;

    public WekaMlTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {


        ConverterUtils.DataSource source = null;
        Instances data = null;
        try {
            InputStream inputStream = mContext.getAssets().open(FILE_URL);
            source = new ConverterUtils.DataSource(inputStream);
            data = source.getDataSet();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // setting class attribute if the data format does not provide this information
        // For example, the XRFF format saves the class attribute information as well
        if (data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() - 1);

        String[] options = new String[1];
        options[0] = "-U";            // unpruned tree
        J48 tree = new J48();         // new instance of tree
        try {
            tree.setOptions(options);     // set the options
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            tree.buildClassifier(data);   // build classifier
        } catch (Exception e) {
            e.printStackTrace();
        }

        Evaluation eval = null;
        try {
            eval = new Evaluation(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            eval.crossValidateModel(tree, data, 10, new Random(1));
            Log.d("errorRate", String.valueOf(eval.errorRate()));
            Log.d("matrix", eval.toMatrixString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
