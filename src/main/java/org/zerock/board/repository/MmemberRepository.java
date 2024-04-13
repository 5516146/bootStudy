package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.board.entity.MMember;

public interface MmemberRepository extends JpaRepository<MMember, Long> {
    
}
