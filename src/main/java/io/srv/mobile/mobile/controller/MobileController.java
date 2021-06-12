package io.srv.mobile.mobile.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.srv.mobile.mobile.modal.Mobile;
import io.srv.mobile.mobile.service.MobileService;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;


@RestController
@RequestMapping("/mobile")
@RequiredArgsConstructor
public class MobileController {

	private final MobileService mobileService;

	@Value("classpath:mobile.graphqls")
	private Resource schemaResource;

	private GraphQL graphQL;

	@PostConstruct
	public void loadSchema() throws IOException {
		File schemaFile = schemaResource.getFile();
		TypeDefinitionRegistry registry = new SchemaParser().parse(schemaFile);
		RuntimeWiring wiring = buildWiring();
		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(registry, wiring);
		graphQL = GraphQL.newGraphQL(schema).build();
	}

	private RuntimeWiring buildWiring() {
		DataFetcher<List<Mobile>> fetcher1 = dataFetchingEnvironment -> {
			return mobileService.getMobiles();
		};

		DataFetcher<Mobile> fetcher2 = dataFetchingEnvironment -> {
			return mobileService.getMobileById(dataFetchingEnvironment.getArgument("id"));
		};
		return RuntimeWiring.newRuntimeWiring().type("Query", typeWriting ->
			typeWriting.dataFetcher("getAll", fetcher1).dataFetcher("getById", fetcher2)).build();
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<Mobile>> getMobiles() {
		return new ResponseEntity<>(mobileService.getMobiles(), HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Mobile> getMobileById(@PathVariable Long id) {
		return new ResponseEntity<>(mobileService.getMobileById(id), HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<Mobile> saveMobile(@RequestBody Mobile mobile) {
		return new ResponseEntity<>(mobileService.saveMobile(mobile), HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Mobile> updateMobile(@RequestBody Mobile mobile) {
		return new ResponseEntity<>(mobileService.updatedMobile(mobile), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteMobile(@PathVariable Long id) {
		mobileService.deleteMobile(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping("/getAll")
	public ResponseEntity<Object> getAllMobiles(@RequestBody String query) {
		ExecutionResult result = graphQL.execute(query);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/getById")
	public ResponseEntity<Object> getMobilById(@RequestBody String query) {
		ExecutionResult result = graphQL.execute(query);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
