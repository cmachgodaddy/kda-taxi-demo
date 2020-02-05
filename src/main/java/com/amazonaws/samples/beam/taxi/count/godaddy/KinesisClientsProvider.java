package com.amazonaws.samples.beam.taxi.count.godaddy;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.producer.IKinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import org.apache.beam.sdk.io.kinesis.AWSClientsProvider;

import java.io.Serializable;

public final class KinesisClientsProvider implements AWSClientsProvider, Serializable {
  private final SerializableSupplier<AmazonKinesis> kinesisClientSupplier;
  private final SerializableSupplier<AmazonCloudWatch> cloudWatchClientSupplier;

  private KinesisClientsProvider(
      final SerializableSupplier<AmazonKinesis> kinesisClientSupplier,
      final SerializableSupplier<AmazonCloudWatch> cloudWatchClientSupplier) {
    this.kinesisClientSupplier = kinesisClientSupplier;
    this.cloudWatchClientSupplier = cloudWatchClientSupplier;
  }

  public static KinesisClientsProvider of() {
      return new KinesisClientsProvider(
          () -> AmazonKinesisClientBuilder.defaultClient(),
          () -> AmazonCloudWatchClientBuilder.defaultClient());
  }

  @Override
  public AmazonKinesis getKinesisClient() {
    return kinesisClientSupplier.get();
  }

  @Override
  public AmazonCloudWatch getCloudWatchClient() {
    return cloudWatchClientSupplier.get();
  }

  @Override
  public IKinesisProducer createKinesisProducer(KinesisProducerConfiguration config) {
    return new KinesisProducer(config);
  }
}
