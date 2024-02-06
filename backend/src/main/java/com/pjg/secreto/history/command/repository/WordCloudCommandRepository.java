package com.pjg.secreto.history.command.repository;

import com.pjg.secreto.history.common.entity.WordCloud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordCloudCommandRepository extends JpaRepository<WordCloud, Long> {

}
