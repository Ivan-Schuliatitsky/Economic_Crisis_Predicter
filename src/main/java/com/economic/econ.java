package com.economic;

import org.apache.spark.SparkConf;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.DecisionTreeClassifier;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.feature.PCA;
import org.apache.spark.ml.feature.PolynomialExpansion;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class econ {
    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "c:\\");
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

        VectorAssembler assembler = new VectorAssembler()//year,systemic_crisis,exch_usd,domestic_debt_in_default,sovereign_external_debt_default,gdp_weighted_default,inflation_annual_cpi,independence,currency_crises,inflation_crises
                .setInputCols(new String[]{"domestic_debt_in_default", "sovereign_external_debt_default"}) //"year", "exch_usd", "domestic_debt_in_default", "sovereign_external_debt_default", "currency_crises"
                .setOutputCol("features");

        train.show();
        train.printSchema();

        //VectorAssembler assembler2 = new VectorAssembler()
        //        .setInputCols(new String[]{"pca_features"})
        //       .setOutputCol("features");

        LogisticRegression trainer = new LogisticRegression()
                .setLabelCol("inflation_annual_cpi")
                .setFeaturesCol("features");

        //Pipeline pipeline = new Pipeline()
        //        .setStages(new PipelineStage[]{indexer, assembler, polyExpansion, pca, assembler2, trainer});
        Pipeline pipeline = new Pipeline()
                .setStages(new PipelineStage[]{indexer, assembler,  trainer});

        PipelineModel pipelineM = pipeline.fit(train);
        //create_prediction(model);
        Dataset<Row> rawPredictions = pipelineM.transform(economic);

        rawPredictions.show(300);

        //Dataset<Row> predictions = enrichPredictions(spark, rawPredictions);

        rawPredictions.show(300);
    }
}
