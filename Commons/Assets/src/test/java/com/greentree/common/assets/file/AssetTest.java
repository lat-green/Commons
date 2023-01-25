package com.greentree.common.assets.file;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.greentree.commons.assets.serializator.manager.AssetManager;
import com.greentree.commons.assets.serializator.request.KeyLoadRequestImpl;
import com.greentree.commons.util.cortege.Pair;

public class AssetTest {
	
	
	private static final long TIMEOUT = 38;
	private AssetManager manager;
	
	static Stream<Pair<KeyLoadRequestImpl<?>, ?>> map_requests() {
		final var res = new ArrayList<Pair<KeyLoadRequestImpl<?>, ?>>();
		for(var n : new int[]{56,89}) {
			final var key = new StringAssetKey(n + "");
			res.add(new Pair<>(new KeyLoadRequestImpl<>(Integer.class, key), n));
		}
		return res.stream();
	}
	
	static Stream<Pair<KeyLoadRequestImpl<?>, ?>> requests() {
		final var res = new ArrayList<Pair<KeyLoadRequestImpl<?>, ?>>();
		for(var str : new String[]{"12345","Hello"}) {
			final var key = new StringAssetKey(str);
			res.add(new Pair<>(new KeyLoadRequestImpl<>(String.class, key), str));
		}
		return res.stream();
	}
	
	@MethodSource(value = "requests")
	@ParameterizedTest
	<T> void AssetManager_canLoad(Pair<KeyLoadRequestImpl<? extends T>, ? extends T> pair) {
		final var request = pair.first;
		manager.addSerializator(new StringAssetSerializator(null));
		
		assertTrue(manager.canLoad(request.loadType(), request.key()));
	}
	
	@Timeout(value = TIMEOUT, unit = TimeUnit.MILLISECONDS)
	@MethodSource(value = "requests")
	@ParameterizedTest
	<T> void AssetManager_canLoad_after_load(
			Pair<KeyLoadRequestImpl<? extends T>, ? extends T> pair) {
		final var request = pair.first;
		manager.addSerializator(new StringAssetSerializator(null));
		
		manager.load(request);
		
		assertTrue(manager.canLoad(request.loadType(), request.key()));
	}
	
	@Timeout(value = TIMEOUT, unit = TimeUnit.MILLISECONDS)
	@MethodSource(value = "requests")
	@ParameterizedTest
	<T> void AssetManager_double_load(Pair<KeyLoadRequestImpl<? extends T>, ? extends T> pair) {
		final var request = pair.first;
		final var result = pair.seconde;
		manager.addSerializator(new StringAssetSerializator(null));
		
		final var res1 = manager.load(request);
		final var res2 = manager.load(request);
		
		assertEquals(res1, res2);
		assertTrue(res1 == res2);
		assertEquals(res1.get(), result);
		assertEquals(res2.get(), result);
	}
	
	@Timeout(value = TIMEOUT, unit = TimeUnit.MILLISECONDS)
	@MethodSource(value = "requests")
	@ParameterizedTest
	<T> void AssetManager_double_loadAsync(
			Pair<KeyLoadRequestImpl<? extends T>, ? extends T> pair) {
		final var request = pair.first;
		final var result = pair.seconde;
		manager.addSerializator(new StringAssetSerializator(null));
		
		final var res1 = manager.loadAsync(request.loadType(), request.key());
		final var res2 = manager.loadAsync(request.loadType(), request.key());
		
		assertEquals(res1, res2);
		assertTrue(res1 == res2);
		assertEquals(res1.get(), result);
		assertEquals(res2.get(), result);
	}
	
	@MethodSource(value = "requests")
	@ParameterizedTest
	<T> void AssetManager_isDeepValid(Pair<KeyLoadRequestImpl<? extends T>, ? extends T> pair) {
		final var request = pair.first;
		manager.addSerializator(new StringAssetSerializator(null));
		
		assertTrue(manager.isDeepValid(request.loadType(), request.key()));
	}
	
	@Timeout(value = TIMEOUT, unit = TimeUnit.MILLISECONDS)
	@MethodSource(value = "requests")
	@ParameterizedTest
	<T> void AssetManager_isDeepValid_after_load(
			Pair<KeyLoadRequestImpl<? extends T>, ? extends T> pair) {
		final var request = pair.first;
		manager.addSerializator(new StringAssetSerializator(null));
		
		manager.load(request);
		
		assertTrue(manager.isDeepValid(request.loadType(), request.key()));
	}
	
	@MethodSource(value = "requests")
	@ParameterizedTest
	<T> void AssetManager_isValid(Pair<KeyLoadRequestImpl<? extends T>, ? extends T> pair) {
		final var request = pair.first;
		manager.addSerializator(new StringAssetSerializator(null));
		
		assertTrue(manager.isValid(request.loadType(), request.key()));
	}
	
	
	@MethodSource(value = "requests")
	@ParameterizedTest
	<T> void AssetManager_isValid_after_load(
			Pair<KeyLoadRequestImpl<? extends T>, ? extends T> pair) {
		final var request = pair.first;
		manager.addSerializator(new StringAssetSerializator(null));
		
		manager.load(request);
		
		assertTrue(manager.isValid(request.loadType(), request.key()));
	}
	
	
	@MethodSource(value = "requests")
	@ParameterizedTest
	<T> void AssetManager_load(Pair<KeyLoadRequestImpl<? extends T>, ? extends T> pair) {
		final var request = pair.first;
		final var result = pair.seconde;
		manager.addSerializator(new StringAssetSerializator(null));
		
		final var res = manager.load(request);
		
		assertEquals(res.get(), result);
	}
	
	@Timeout(value = TIMEOUT, unit = TimeUnit.MILLISECONDS)
	@MethodSource(value = "requests")
	@ParameterizedTest
	<T> void AssetManager_load_with_Default(
			Pair<KeyLoadRequestImpl<? extends T>, ? extends T> pair) {
		final var request = pair.first;
		final var result = pair.seconde;
		final var DEF_STR = "DEF_STR";
		manager.addSerializator(new StringAssetSerializator(DEF_STR));
		
		final var res = manager.load(request);
		
		assertEquals(res.get(), result);
	}
	
	
	@Timeout(value = TIMEOUT, unit = TimeUnit.MILLISECONDS)
	@MethodSource(value = "requests")
	@ParameterizedTest
	<T> void AssetManager_loadAsync(Pair<KeyLoadRequestImpl<? extends T>, ? extends T> pair) {
		final var request = pair.first;
		final var result = pair.seconde;
		manager.addSerializator(new StringAssetSerializator(null));
		
		final var res = manager.loadAsync(request.loadType(), request.key());
		
		assertEquals(res.get(), result);
	}
	
	@Timeout(value = TIMEOUT, unit = TimeUnit.MILLISECONDS)
	@MethodSource(value = "requests")
	@ParameterizedTest
	<T> void AssetManager_loadAsync_with_Default(
			Pair<KeyLoadRequestImpl<? extends T>, ? extends T> pair) {
		final var request = pair.first;
		final var DEF_STR = "DEF_STR";
		manager.addSerializator(new StringAssetSerializator(DEF_STR));
		
		final var res = manager.loadAsync(request.loadType(), request.key());
		
		assertEquals(res.get(), DEF_STR);
	}
	
	
	@Timeout(value = TIMEOUT, unit = TimeUnit.MILLISECONDS)
	@MethodSource(value = "map_requests")
	@ParameterizedTest
	<T> void AssetManager_map_load(Pair<KeyLoadRequestImpl<? extends T>, ? extends T> pair) {
		manager.addSerializator(new StringAssetSerializator(null));
		manager.addSerializator(new StringToIntAssetSerializator());
		
		final var res = manager.load(pair.first);
		
		assertEquals(res.get(), pair.seconde);
	}
	
	@BeforeEach
	void setup() {
		manager = new AssetManager(new NullExecutorService());
	}
	
}
