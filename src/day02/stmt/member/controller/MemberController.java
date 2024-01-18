package day02.stmt.member.controller;

import java.util.List;

import day02.stmt.member.model.dao.MemberDAO;
import day02.stmt.member.model.vo.Member;

public class MemberController {
//	List<Member> memberList;
	MemberDAO mDao;
	
	
	public MemberController() {
		mDao = new MemberDAO();
	}
	
	//mDao.selectAll(); // 이렇게 하면 안됨
	public List<Member> printAll() {
		List<Member>mList = mDao.selectAll();
		return mList;
	}
	
	public Member printOneById(String memberId) {
		Member member = mDao.selectOneById(memberId);
		return member;
	}
	
	public void registerMember(Member member) {
		mDao.insertMember(member);
	}
	
	public void removeMember(String memberId) {
		mDao.deleteMember(memberId);
	}
	
	public void modifyMember(Member member) {
		mDao.updateMember(member);
	}
}
