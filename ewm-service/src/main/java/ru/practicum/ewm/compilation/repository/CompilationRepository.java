package ru.practicum.ewm.compilation.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.compilation.entity.Compilation;

@Repository
public interface CompilationRepository extends R2dbcRepository<Compilation, Integer>, CustomCompilationRepository {
}

