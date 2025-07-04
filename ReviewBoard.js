import styles from "../styles/components/ReviewBoard.module.css";

<div className={styles["review-container"]}>
  <h2 className={styles["review-title"]}>후기 게시판</h2>
  <div className={styles["review-buttonWrapper"]}>
    <button className={styles["review-btn"]} onClick={handleWriteClick}>후기 작성</button>
  </div>

  <table className={styles["review-table"]}>
    <thead className={styles["review-thead"]}>
      <tr className={styles["review-tr"]}>
        <th className={styles["review-th"]}>번호</th>
        <th className={styles["review-th"]}>제목</th>
        <th className={styles["review-th"]}>작성자</th>
        <th className={styles["review-th"]}>별점</th>
        <th className={styles["review-th"]}>작성일자</th>
        <th className={styles["review-th"]}>조회수</th>
      </tr>
    </thead>
    <tbody>
      {reviews.map((review, index) => (
        <tr key={review.revId} className={styles["review-tr"]}>
          <td className={styles["review-td"]}>{index + 1}</td>
          <td className={styles["review-td"]}>
            <button
              className={styles["review-revttl"]}
              onClick={() => navigate(`/review/${review.revId}`)}
            >
              {review.revTtl}
            </button>
          </td>
          <td className={styles["review-td"]}>{review.memberId}</td>
          <td className={styles["review-td"]}>{review.revStars}</td>
          <td className={styles["review-td"]}>
            {new Date(review.revRegDate).toLocaleDateString()}
          </td>
          <td className={styles["review-td"]}>{review.revViews}</td>
        </tr>
      ))}
    </tbody>
  </table>
  <div className={styles["review-page"]}>
    <button disabled={page === 1} onClick={() => setPage(page - 1)}>
      이전
    </button>
    {[...Array(totalPages)].map((_, i) => (
      <button
        key={i + 1}
        onClick={() => setPage(i + 1)}
        className={page === i + 1 ? styles.active : ""}
      >
        {i + 1}
      </button>
    ))}
    <button
      disabled={page === totalPages}
      onClick={() => setPage(page + 1)}
    >
      다음
    </button>
  </div>
</div> 