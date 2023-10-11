package com.obsidian.util;

import com.obsidian.ImplMultiNoiseSampler;
import com.obsidian.ObsidianMod;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.ArrayList;
import java.util.List;

public class WeightedNoiseSelection<T> {
    private final List<WeightedEntry<T>> entries;
    private PerlinNoiseSampler perlinNoiseSampler;
    public double totalWeight;

    public WeightedNoiseSelection() {
        entries = new ArrayList<>();
    }

    public void add(T item, double weight) {
        entries.add(new WeightedEntry<>(item, weight));
        totalWeight += weight;
    }

    public boolean resetPerlinNoiseSampler() {
        if(perlinNoiseSampler == null) {
            return false;
        }

        perlinNoiseSampler = null;
        return true;
    }
    public double maxNum;
    public double minNum;
    public double average;
    public double count;
    public T pickFromNoise(MultiNoiseUtil.MultiNoiseSampler sampler, double x, double y, double z) {
        if(perlinNoiseSampler == null) {
            perlinNoiseSampler = new PerlinNoiseSampler(new ChunkRandom(new CheckedRandom(((ImplMultiNoiseSampler)(Object)sampler).getSeed())));
            ObsidianMod.LOGGER.info("Created perlinNoiseSampler for WeightedNoiseSelection");
        }


        double target = (Math.abs(perlinNoiseSampler.sample(x / 16, y / 16, z / 16)) * 3) * totalWeight;

        double currentWeight = 0;

        for(WeightedEntry<T> entry : entries) {
            currentWeight += entry.getWeight();
            if(currentWeight >= target) {
                return entry.getValue();
            }
        }

        return null;
    }

    public class WeightedEntry<T> {
        private double weight;
        private T value;

        public WeightedEntry(T value, double weight) {
            this.value = value;
            this.weight = weight;
        }
        public T getValue() {
            return value;
        }
        public double getWeight() {
            return weight;
        }
    }
}
