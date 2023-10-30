package board.BulletinBoard.service;

import board.BulletinBoard.domain.dto.MemberDto;
import board.BulletinBoard.domain.Member;
import board.BulletinBoard.domain.Role;
import board.BulletinBoard.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
    private MemberRepository memberRepository;

    @Transactional
    public Long joinMember(MemberDto memberDto){
        //비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(memberDto.toEntity()).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException{
        Optional<Member> memberWrapper = memberRepository.findByEmail(memberEmail);
        Member member = memberWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(("admin@example.com").equals(memberEmail)){
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new User(member.getEmail(), member.getPassword(), authorities);
    }

    public MemberDto findByEmail(String memberEmail){
        Optional<Member> memberWrapper = memberRepository.findNicknameByEmail(memberEmail);
        Member member = memberWrapper.get();
        return MemberDto.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .password(member.getPassword())
                .build();
    }
}
