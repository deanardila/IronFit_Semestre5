package com.ironfit.backendmongo.test;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/test")

public class TestController {
    private final TestRepo repo;
    public TestController(TestRepo testRepo) {
        this.repo = testRepo;
    }

    @PostMapping
    public TestDoc crear(@RequestBody TestDoc body) {
        return repo.save(new TestDoc(body.getNombre()));
    }

    @GetMapping
    public List<TestDoc> listar() {
        return repo.findAll();
    }
}
