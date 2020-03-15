package com.economic;

import org.apache.spark.SparkConf;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.feature.PCA;
import org.apache.spark.ml.feature.PolynomialExpansion;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class regresion {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        System.setProperty("hadoop.home.dir", "C:\\winutils\\");
        SparkConf conf = new SparkConf().setMaster("local[4]").setAppName("ML").set("spark.driver.host", "localhost");
        conf.set("spark.testing.memory", "2147480000");

        SparkSession spark = SparkSession.builder()
                .config(conf)
                .getOrCreate();

        spark.sparkContext().setLogLevel("ERROR");


        Dataset<Row> train = spark.read()
                .option("delimiter", ",")
                .option("inferSchema", "true")
                .option("header", "true")
                .csv("src/main/resources/african_crises.csv");

        Dataset<Row> economic = spark.read()
                .option("delimiter", ",")
                .option("inferSchema", "true")
                .option("header", "true")
                .csv("src/main/java/com/nado/data.csv");

        StringIndexer indexer = new StringIndexer()
                .setInputCol("banking_crisis")
                .setOutputCol("banking_crisis_indexed")
                .setHandleInvalid("keep");

        // VectorAssembler assembler = new VectorAssembler()
        //       .setInputCols(new String[]{"year", "systemic_crisis", "exch_usd", "domestic_debt_in_default", "sovereign_external_debt_default", "currency_crises"})
        //        .setOutputCol("non_features");
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"exch_usd", "domestic_debt_in_default", "sovereign_external_debt_default", "gdp_weighted_default", "inflation_annual_cpi", "independence", "currency_crises", "inflation_crises"}) //systemic_crises
                .setOutputCol("features");


        train.show();
        train.printSchema();

        PolynomialExpansion polyExpansion = new PolynomialExpansion()
                .setOutputCol("polyFeatures")
                .setDegree(2);

        PCA pca = new PCA()
                .setInputCol("polyFeatures")
                .setK(20)
                .setOutputCol("pca_features");

        //VectorAssembler assembler2 = new VectorAssembler()
        //        .setInputCols(new String[]{"pca_features"})
        //       .setOutputCol("features");

        LogisticRegression trainer = new LogisticRegression()
                .setLabelCol("banking_crisis_indexed")
                .setFeaturesCol("features");

        //Pipeline pipeline = new Pipeline()
        //        .setStages(new PipelineStage[]{indexer, assembler, polyExpansion, pca, assembler2, trainer});
        Pipeline pipeline = new Pipeline()
                .setStages(new PipelineStage[]{indexer, assembler, trainer});

        PipelineModel model = pipeline.fit(train);
        //create_prediction(model);
        Dataset<Row> rawPredictions = model.transform(economic);
        List<Row> list = rawPredictions.collectAsList();
        PrintWriter writer = new PrintWriter("src/main/resources/test.txt", "UTF-8");
        writer.println("Country ; Year ; Systemic_crisis ; USD ; GDP ; Inflation ; Inflation_Crises ; OUTPUT");
        for (Row row : list) {
            writer.print(String.valueOf(row.get(2)));
            writer.append(" | ");
            writer.print(String.valueOf(row.get(3)));
            writer.append(" | ");
            if (String.valueOf(row.get(4)).equals("1.0")) {
                writer.print("crisis");
            } else {
                writer.print("no_crisis");
            }
            writer.append(" | ");
            writer.print(String.valueOf(row.get(5)));
            writer.append(" | ");
            writer.print(String.valueOf(row.get(8)));
            writer.append(" | ");
            writer.print(String.valueOf(row.get(9)));
            writer.append(" | ");
            writer.print(String.valueOf(row.get(12)));
            writer.append(" | ");
            writer.print(String.valueOf(row.get(16)));
            writer.print("\n");
        }
        writer.close();

        System.out.println(rawPredictions.toString());
        rawPredictions.show(300);
    }
}

