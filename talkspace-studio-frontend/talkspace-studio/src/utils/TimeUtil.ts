import dayjs from 'dayjs';

export const formatCreateTime = (createTimeArray: number[]): string => {
  if (!Array.isArray(createTimeArray) || createTimeArray.length !== 6) {
    throw new Error('无效的 create time 数组，需要包含 [year, month, day, hour, minute, second]');
  }

  // 构造 Date 对象（注意月份需要 -1）
  const [year, month, day, hour, minute, second] = createTimeArray;
  const date = new Date(year, month - 1, day, hour, minute, second);

  // 使用 dayjs 格式化
  return dayjs(date).format('YYYY-MM-DD HH:mm:ss');
}