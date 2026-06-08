package com.douzone.knitching.domain.pattern.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.douzone.knitching.domain.pattern.dto.PatternDetailResponse;
import com.douzone.knitching.domain.pattern.dto.PatternListResponse;
import com.douzone.knitching.domain.pattern.entity.Pattern;

@Repository
public interface PatternRepository extends JpaRepository<Pattern, Long> {
	@Query("""
			SELECT new com.douzone.knitching.domain.pattern.dto.PatternListResponse(
				p.patternId,
				p.thumbnailUrl,
				p.title,
				p.instId,
				u.nickname,
				p.tool,
				p.price
			)
			FROM Pattern p
			LEFT JOIN Instructor i ON p.instId = i.instId
			LEFT JOIN i.user u
			WHERE (:tool IS NULL OR p.tool = :tool)
			ORDER BY p.createdAt DESC
			""")
	Page<PatternListResponse> findPatternListLatest(@Param("tool") String tool, Pageable pageable);

	@Query("""
			SELECT new com.douzone.knitching.domain.pattern.dto.PatternListResponse(
				p.patternId,
				p.thumbnailUrl,
				p.title,
				p.instId,
				u.nickname,
				p.tool,
				p.price
			)
			FROM Pattern p
			LEFT JOIN Instructor i ON p.instId = i.instId
			LEFT JOIN i.user u
			WHERE (:tool IS NULL OR p.tool = :tool)
			ORDER BY (p.likeCount * 10 + p.viewCount) DESC
			""")
	Page<PatternListResponse> findPatternListPopular(@Param("tool") String tool, Pageable pageable);

	@Query("""
			SELECT new com.douzone.knitching.domain.pattern.dto.PatternDetailResponse(
				p.patternId,
				p.thumbnailUrl,
				p.title,
				p.price,
				p.instId,
				u.nickname,
				u.userId,
				u.loginId,
				u.email,
				p.difficulty,
				p.tool,
				p.skillLevel,
				p.content,
				p.likeCount,
				p.viewCount,
				p.enrollCount,
				p.rating
			)
			FROM Pattern p
			LEFT JOIN Instructor i ON p.instId = i.instId
			LEFT JOIN i.user u
			WHERE p.patternId = :patternId
			""")
	Optional<PatternDetailResponse> findPatternDetailByPatternId(@Param("patternId") Long patternId);
}
